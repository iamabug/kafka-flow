import com.fasterxml.jackson.databind.ObjectMapper;
import iamabug.common.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JacksonTest {
    public static void main(String[] args) throws IOException {
        Map<String, Object> m = new HashMap();
        m.put("value", 1);
        m.put("key", "xx");
        new ObjectMapper().writeValue(System.out, new Message(Message.TYPE.KAFKA_CONSUME).put("xx", m));
    }
}
