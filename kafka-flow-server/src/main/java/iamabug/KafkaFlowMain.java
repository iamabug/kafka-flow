package iamabug;

import iamabug.common.Constants;
import iamabug.kafka.KafkaDummyServlet;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;

public class KafkaFlowMain {

    enum RunMode {
        PROD,
        DEV
    }

    private Path basePath;

    public static void main(String[] args) {
        try {
            new KafkaFlowMain().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws Exception {
        Server server = new Server(12345);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);

        // 前端页面
        WebAppContext webapp = new WebAppContext();
        contexts.addHandler(webapp);
        // JSP support
        Configuration.ClassList classlist = Configuration.ClassList
                .setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration",
                "org.eclipse.jetty.plus.webapp.EnvConfiguration",
                "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");
        enableEmbeddedJspSupport(webapp);
        switch (getRunMode()) {
            case PROD:
                webapp.setWar(basePath.toString());
                break;
            case DEV:
                webapp.setBaseResource(new PathResource(basePath.resolve("src/main/webapp")));
                break;
            default:
                throw new FileNotFoundException("Cannot find war file or webapp directory.");

        }

        // WebSocket
        ServletContextHandler ws = new ServletContextHandler(contexts, "/ws", ServletContextHandler.SESSIONS);
        ws.addServlet(new ServletHolder("ws", KafkaDummyServlet.class), "/kafka/dummy");

        server.start();
        server.join();
    }

    /**
     * Reference to https://github.com/jetty-project/embedded-jetty-live-war
     * @return
     * @throws IOException
     */
    private RunMode getRunMode() throws IOException
    {
        String warLocation = System.getProperty(Constants.PROPERTY_WAR_LOCATION);
        if (warLocation != null)
        {
            Path warPath = new File(warLocation).toPath().toRealPath();
            if (Files.exists(warPath) && Files.isDirectory(warPath))
            {
                this.basePath = warPath;
                return RunMode.PROD;
            }
        }

        Path devPath = new File("./kafka-flow-web").toPath().toRealPath();
        if (Files.exists(devPath) && Files.isDirectory(devPath))
        {
            this.basePath = devPath;
            return RunMode.DEV;
        }

        return null;
    }

    /**
     * Reference to https://github.com/jetty-project/embedded-jetty-jsp
     * @param servletContextHandler
     * @throws IOException
     */
    private void enableEmbeddedJspSupport(ServletContextHandler servletContextHandler) throws IOException
    {
        // Establish Scratch directory for the servlet context (used by JSP compilation)
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");

        if (!scratchDir.exists())
        {
            if (!scratchDir.mkdirs())
            {
                throw new IOException("Unable to create scratch directory: " + scratchDir);
            }
        }
        servletContextHandler.setAttribute("javax.servlet.context.tempdir", scratchDir);

        // Set Classloader of Context to be sane (needed for JSTL)
        // JSP requires a non-System classloader, this simply wraps the
        // embedded System classloader in a way that makes it suitable
        // for JSP to use
        ClassLoader jspClassLoader = new URLClassLoader(new URL[0], this.getClass().getClassLoader());
        servletContextHandler.setClassLoader(jspClassLoader);

        // Manually call JettyJasperInitializer on context startup
        servletContextHandler.addBean(new JspStarter(servletContextHandler));

        // Create / Register JSP Servlet (must be named "jsp" per spec)
        ServletHolder holderJsp = new ServletHolder("jsp", JettyJspServlet.class);
        holderJsp.setInitOrder(0);
        holderJsp.setInitParameter("logVerbosityLevel", "DEBUG");
        holderJsp.setInitParameter("fork", "false");
        holderJsp.setInitParameter("xpoweredBy", "false");
        holderJsp.setInitParameter("compilerTargetVM", "1.8");
        holderJsp.setInitParameter("compilerSourceVM", "1.8");
        holderJsp.setInitParameter("keepgenerated", "true");
        servletContextHandler.addServlet(holderJsp, "*.jsp");
    }

    /**
     * Reference to https://github.com/jetty-project/embedded-jetty-jsp
     */
    public static class JspStarter extends AbstractLifeCycle implements ServletContextHandler.ServletContainerInitializerCaller
    {
        JettyJasperInitializer sci;
        ServletContextHandler context;

        public JspStarter (ServletContextHandler context)
        {
            this.sci = new JettyJasperInitializer();
            this.context = context;
            this.context.setAttribute("org.apache.tomcat.JarScanner", new StandardJarScanner());
        }

        @Override
        protected void doStart() throws Exception
        {
            ClassLoader old = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(context.getClassLoader());
            try
            {
                sci.onStartup(null, context.getServletContext());
                super.doStart();
            }
            finally
            {
                Thread.currentThread().setContextClassLoader(old);
            }
        }
    }
}

