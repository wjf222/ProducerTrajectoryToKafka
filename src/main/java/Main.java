import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static final String filePath = "/home/bestmachine/Documents/TrajectoryProducer/src/main/resources/tragectory.txt";
    public static void main(String[] args) throws InterruptedException, IOException {
        Logger logger = LoggerFactory.getLogger("TrajectorProducer");
        Properties kafkaProps = new Properties();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.245.217:9092");
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put(ProducerConfig.BATCH_SIZE_CONFIG,10);
        kafkaProps.put(ProducerConfig.LINGER_MS_CONFIG,5);
        KafkaProducer producer = new KafkaProducer<String,String>(kafkaProps);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        CountDownLatch countDownLatch = new CountDownLatch(100);
        while((line = br.readLine()) != null) {
            ProducerRecord<String, String> record = new ProducerRecord<>("trajector", "hello");
            String finalLine = line;
            producer.send(record, (recordMetadata, e) -> {
                logger.info("Send Successful. Value = {}", finalLine);
                countDownLatch.countDown();
                if(e != null) {
                    logger.error("Send Error：{}",e);
                }
            });
        }
        countDownLatch.await();
    }
}
