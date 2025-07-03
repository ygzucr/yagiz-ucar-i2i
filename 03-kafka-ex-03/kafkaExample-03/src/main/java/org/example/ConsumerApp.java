package org.example;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerApp {
    public static final String TOPIC = "student-topic";

    public static void main(String[] args) {

        Properties props = new Properties();

        props.put("bootstrap.servers", "13.51.207.134:9092");
        props.put("group.id", "student-group6");
        props.put("client.id", "student-consumer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(TOPIC));

        Gson gson = new Gson();

        System.out.println("Kafka Consumer started, waiting for message...");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {

                Student student = gson.fromJson(record.value(), Student.class);
                System.out.println("Student from Kafka producer: " + student);
            }
        }
    }
}
