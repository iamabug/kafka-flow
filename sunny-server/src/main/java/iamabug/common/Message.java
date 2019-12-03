package iamabug.common;

import java.util.HashMap;
import java.util.Map;

public class Message {
    public enum TYPE {
        CMD_START_CONSUME,
        CMD_STOP_CONSUME,
        CMD_RESUME_CONSUME,
        MESSAGES_CONSUMED,
        CMD_LIST_TOPICS,
        RESULT_LIST_TOPICS,

        KAFKA_CONSUME,
        STOP_CONSUME,
        TOPIC_LIST,
        KAFKA_MESSAGES,
        KAFKA_PRODUCE,
        PING,
        ERROR_INFO,
    }
    public TYPE type;
    public Map<String, Object> data = new HashMap<>();

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }

    public Message(TYPE type) {
        this.type = type;
    }
    public Message put(String k, Object v) {
        data.put(k, v);
        return this;
    }

    public Message() {
    }
}
