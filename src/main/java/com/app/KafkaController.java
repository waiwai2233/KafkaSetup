package com.app;

// import com.app.KafkaProducerUtil;
// import org.apache.kafka.clients.admin.AdminClient;
// import org.apache.kafka.clients.admin.NewTopic;
// import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.config.TopicBuilder;
// import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

// import java.util.Collections;
// import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    // @Autowired
    // private KafkaAdmin kafkaAdmin;

    @Autowired
    private KafkaProducerUtil kafkaProducerUtil;

    @PostMapping("/createTopic")
    public String createTopic(@RequestParam String name) {
        try (Producer<String, String> producer = kafkaProducerUtil.getProducer()) {
            ProducerRecord<String, String> record = new ProducerRecord<>(name, "Hello, Kafka FROM API!");
            producer.send(record);
            return "Topic created successfully";
        } catch (Exception e) {
            return "Failed to create topic: " + e.getMessage();
        }
    }
}
