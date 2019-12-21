package iamabug.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Message {

    private static ObjectMapper mapper = new ObjectMapper();

    public enum TYPE {
        CMD_START_CONSUME,
        CMD_STOP_CONSUME,
        MESSAGES_CONSUMED,
        CMD_PRODUCE,
        PRODUCE_DONE,

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

    public static Message fromJson(String json) throws JsonProcessingException {
        return mapper.readValue(json, Message.class);
    }

    public String toJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

    public static String error(Exception e) throws JsonProcessingException {
        return new Message(TYPE.ERROR_INFO).put("error", e).toJson();
    }
}
