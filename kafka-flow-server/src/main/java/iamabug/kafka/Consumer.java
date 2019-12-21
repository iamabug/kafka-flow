package iamabug.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import iamabug.common.Message;
import iamabug.conf.ClusterConfigurations;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class Consumer extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private RemoteEndpoint client;
    private static Random random = new Random();
    private ConsumerRecords<String, String> records;
    private Message msg;
    private volatile boolean running = false;
    private KafkaConsumer consumer;

    public Consumer(RemoteEndpoint client, Message msg) {
        this.client = client;
        this.msg = msg;
        consumer = createConsumer(msg);
    }

    @Override
    public void run() {
        running = true;
        consumer.subscribe(Collections.singletonList(msg.data.get("topic")));
        try {
            while (running) {
                records = consumer.poll(Duration.ofSeconds(2));
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
            logger.info("Closing KafkaConsumer for client {}.", client.getInetSocketAddress().getHostName());
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
        props.put("bootstrap.servers",
                Objects.requireNonNull(ClusterConfigurations.getBootstrapServerByName((String) msg.data.get("cluster"))));
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", msg.data.get("offset"));
        props.put("group.id", "kafka-flow" + random.nextInt());
        return new KafkaConsumer<String, String>(props);
    }


    public void stopConsuming() {
        logger.info("Stop consuming message for client {}.", client.getInetSocketAddress().getHostName());
        running = false;
    }
}
