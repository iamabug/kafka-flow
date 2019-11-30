import com.fasterxml.jackson.databind.ObjectMapper;
import iamabug.common.Message;

import java.io.IOException;

public class JacksonTest {
    public static void main(String[] args) throws IOException {
        new ObjectMapper().writeValue(System.out, new Message(Message.TYPE.KAFKA_CONSUME));
    }
}
