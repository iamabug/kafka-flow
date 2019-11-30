package iamabug.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iamabug.common.Message;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;

public class KafkaSocket extends WebSocketAdapter {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
        super.onWebSocketBinary(payload, offset, len);
        System.out.println(payload);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        System.out.println("websocket closed.");
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        try {
            sess.getRemote().sendString("ping");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("websocket connected.");
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        System.out.println("websocket error.");
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        System.out.println("receive message: " + message);
        try {
            Message msg = mapper.readValue(message, Message.class);
            switch (msg.type) {
                case KAFKA_CONSUME:
                    new Consumer(getRemote()).responde(msg);
                    break;
                default:
                    break;
            }
            System.out.println(msg.type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            try {
                getSession().getRemote().sendString(mapper.writeValueAsString(new Message(Message.TYPE.ERROR_INFO).put("error_info", e)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
