package iamabug;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        Server server = new Server(12345);
        WebAppContext context = new WebAppContext("./sunny-web/src/main/webapp", "/");
        server.setHandler(context);
        server.start();
        server.join();
    }
}

