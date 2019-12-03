package iamabug.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iamabug.common.Message;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;

public class KafkaSocket extends WebSocketAdapter {

    private static ObjectMapper mapper = new ObjectMapper();
    private Consumer consumer;

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
        super.onWebSocketBinary(payload, offset, len);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        System.out.println("websocket closed: " + getSession().getRemoteAddress().getHostString());
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        System.out.println("websocket connected: " + sess.getRemoteAddress().getAddress());
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        System.out.println("websocket error: " + getSession().getRemoteAddress().getHostString());
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        try {
            Message msg = mapper.readValue(message, Message.class);
            System.out.println(msg.type);
            switch (msg.type) {
                case CMD_START_CONSUME:
                    System.out.println("cmd_start_consume");
                    consumer = new Consumer(getRemote(), msg);
                    consumer.start();
                    break;
                case CMD_STOP_CONSUME:
                    consumer.stopConsuming();
                    break;
                case CMD_LIST_TOPICS:
                    break;
                default:
                    break;
            }
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
