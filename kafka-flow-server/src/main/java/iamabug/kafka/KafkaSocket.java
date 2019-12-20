package iamabug.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iamabug.common.Message;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class KafkaSocket extends WebSocketAdapter {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSocket.class);
    private static ObjectMapper mapper = new ObjectMapper();
    private Consumer consumer;

    private String getClientAddress() {
        return getRemote().toString();
    }
    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
        super.onWebSocketBinary(payload, offset, len);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        logger.info("websocket closed: {}", getClientAddress());
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        logger.info("websocket connected: {}", getClientAddress());
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        logger.info("websocket error: {}", getClientAddress());
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        try {
            Message msg = Message.fromJson(message);
            logger.info("Receive websocket message {} from {}", msg, getClientAddress());
            switch (msg.type) {
                case CMD_START_CONSUME:
                    consumer = new Consumer(getRemote(), msg);
                    consumer.start();
                    break;
                case CMD_STOP_CONSUME:
                    consumer.stopConsuming();
                    break;
                default:
                    break;
            }
        } catch (JsonProcessingException e) {
            logger.error("Exception occurred while processing json: {0}, sending this to client", e);
            e.printStackTrace();
            try {
                getSession().getRemote().sendString(
                        Message.error(e)
                );
            } catch (IOException ex) {
                logger.error("Exception occurred while sending error messages back to client: {0}", ex);
                ex.printStackTrace();
            }

        }
    }
}
