package com.github.pteczar.kafka.learning_kafka;
//this import is required to use properties!!!
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


public class ProducerDemo {

    public static void main(String[] args) {
        String bootstrapServers = "127.0.0.1:9092";
    //creating Producers properties
    Properties properties = new Properties();

    //this is how it was done before - the old way:
    /*properties.setProperty("bootstrap.servers", bootstrapServers);
    properties.setProperty("key.serializer", StringSerializer.class.getName());
    properties.setProperty("value.serializer", StringSerializer.class.getName());*/
    //The new way:
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


    //create the producer

        KafkaProducer <String, String> producer = new KafkaProducer<String, String>(properties);

    //create the producer record
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "hello");

    //send data
        producer.send(record);
    //to send data we need to do flush data
        //producer.flush();
    //this is if we want to flush and close the producer
        producer.close();
    }
}
