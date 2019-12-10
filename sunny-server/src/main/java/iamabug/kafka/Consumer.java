package iamabug.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import iamabug.common.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;

import java.io.IOException;
import java.util.*;

public class Consumer extends Thread{
    private RemoteEndpoint client;
    private static Random random = new Random();
    ConsumerRecords<String, String> records;
    private Message msg;
    private volatile boolean running = false;

    public Consumer(RemoteEndpoint client, Message msg) {
        this.client = client;
        this.msg = msg;
    }

    @Override
    public void run() {
        running = true;
        KafkaConsumer consumer = createConsumer(msg);
        consumer.subscribe(Collections.singletonList(msg.data.get("topic")));
        try {
            while (running) {
                records = consumer.poll(1000);
                if (records.count() > 0) {
                    List<Map<String, String>> messages = new ArrayList<>();
                    records.forEach(record -> messages.add(assembleMessage(record)));
                    Message resp = new Message(Message.TYPE.MESSAGES_CONSUMED);
                    resp.put("messages", messages);
                    resp.put("total", records.count());
                    client.sendString(resp.toJson());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    private Map assembleMessage(ConsumerRecord record) {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", record.offset());
//        map.put("partition", record.partition());
        map.put("key", record.key());
        map.put("value", record.value());
        return map;
    }

    private KafkaConsumer createConsumer(Message msg) {
        Properties props = new Properties();
        props.put("bootstrap.servers", msg.data.get("bootstrap-server"));
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", msg.data.get("offset"));
        props.put("group.id", "kafka-flow-" + random.nextInt());
        return new KafkaConsumer<String, String>(props);
    }


    public void stopConsuming() {
        running = false;
    }
}
