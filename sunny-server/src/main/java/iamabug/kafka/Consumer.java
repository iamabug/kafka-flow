package iamabug.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.util.List;
import iamabug.common.Message;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Consumer {
    private RemoteEndpoint client;
    private static ObjectMapper mapper = new ObjectMapper();
    public Consumer(RemoteEndpoint client) {
        this.client = client;
    }
    public void responde(Message msg) {
        try {
            Message resp = new Message(Message.TYPE.KAFKA_MESSAGES);
            resp.put("messages", List.of("first message", "second message"));
            resp.put("total", 2);
            client.sendString(mapper.writeValueAsString(resp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
