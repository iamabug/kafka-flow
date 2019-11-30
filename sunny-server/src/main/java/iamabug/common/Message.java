package iamabug.common;

import java.util.HashMap;
import java.util.Map;

public class Message {
    public enum TYPE {
        KAFKA_CONSUME,
        KAFKA_MESSAGES,
        KAFKA_PRODUCE,
        ERROR_INFO
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
