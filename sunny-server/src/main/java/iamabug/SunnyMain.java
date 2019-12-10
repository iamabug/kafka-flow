package iamabug;

import iamabug.kafka.KafkaDummyServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class SunnyMain {
    public static void main(String[] args) throws Exception {
        Server server = new Server(12345);
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);

        // 前端页面
        WebAppContext webapp = new WebAppContext(contexts, "./sunny-web/src/main/webapp", "/");

        // WebSocket
        ServletContextHandler ws = new ServletContextHandler(contexts, "/ws", ServletContextHandler.SESSIONS);
        ws.addServlet(new ServletHolder("ws", KafkaDummyServlet.class), "/kafka/dummy");

        server.start();
        server.join();
    }
}

