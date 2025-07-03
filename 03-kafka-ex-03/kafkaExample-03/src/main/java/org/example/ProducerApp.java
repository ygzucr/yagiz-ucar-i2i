package org.example;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerApp {

    public static final String TOPIC = "student-topic";

    public static void main(String[] args) {

        Properties settings = new Properties();

        settings.put("client.id", "student-producer");
        settings.put("bootstrap.servers", "13.51.207.134:9092");
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Student student = new Student(1, "Yağız Uçar");
        Gson gson = new Gson();
        String studentJson = gson.toJson(student); // Student objesini JSON string'e çevir

        System.out.println("Sending: " + studentJson);

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(settings)) {
            producer.send(new ProducerRecord<>(TOPIC, "student-1", studentJson));
            System.out.println("Message sent!");
        }
    }
}

