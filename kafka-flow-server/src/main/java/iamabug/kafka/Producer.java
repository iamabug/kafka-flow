package iamabug.kafka;

import iamabug.common.Message;
import iamabug.conf.ClusterConfigurations;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private RemoteEndpoint client;


    private KafkaProducer producer;

    public Producer(RemoteEndpoint client, Message msg) {
        this.client = client;
        this.producer = createProducer(msg);
    }


    public void produce(Message msg) {
        String[] messages = ((String) msg.getData().get("messages")).split("\n");
        String topic = (String) msg.getData().get("topic");
        for (String line : messages) {
            producer.send(new ProducerRecord<>(topic, "", line));
        }

        try {
            client.sendString(new Message(Message.TYPE.PRODUCE_DONE).toJson());
        } catch (IOException e) {
            logger.error("Exception occurred when sending CMD_PRODUCE to client {}: {}",
                    client.getInetSocketAddress().getHostString(), e);
        }

    }

    private KafkaProducer createProducer(Message msg) {
        Properties props = new Properties();
        props.put("bootstrap.servers",
                Objects.requireNonNull(ClusterConfigurations.getBootstrapServerByName((String) msg.data.get("cluster"))));
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer(props);
    }

}
