package iamabug;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class WebSocketClientTest {
    public static void main(String[] args) throws Exception{
        String destUri = "ws://localhost:12345/ws/kafka/dummy";
        if (args.length > 0) {
            destUri = args[0];
        }
        // 创建 WebSocketClient 对象
        WebSocketClient client = new WebSocketClient();
        SimpleEchoSocket socket = new SimpleEchoSocket();
        client.start();

        URI echoUri = new URI(destUri);
        ClientUpgradeRequest request = new ClientUpgradeRequest();
        // 发送 HTTP Upgrade 请求，建立连接
        client.connect(socket, echoUri, request);
        System.out.printf("Connecting to : %s%n", echoUri);

        // 等待关闭
        socket.awaitClose(5, TimeUnit.SECONDS);
    }
}
