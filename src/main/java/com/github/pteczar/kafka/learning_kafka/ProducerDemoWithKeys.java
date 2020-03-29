package com.github.pteczar.kafka.learning_kafka;
//this import is required to use properties!!!

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class ProducerDemoWithKeys {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
         //Logger
        final Logger logger;
        logger = LoggerFactory.getLogger(ProducerDemoWithKeys.class);


        String bootstrapServers = "127.0.0.1:9092";
    //creating Producers properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


    //create the producer

        KafkaProducer <String, String> producer = new KafkaProducer<String, String>(properties);
for (int i = 0; i < 10; i++) {
    //create the producer record

    String topic = "first_topic";
    String value = "hello with keys " + Integer.toString(i);
    String key = "Key id_" + Integer.toString(i);
    ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
    logger.info("Key: " + key); // log the key
    //send data
    producer.send(record, new Callback() {
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            //executes every time a record is successfully sent or exception is thrown
            if (e == null) {
                //the record was successfully sent
                logger.info("Receive new metadata \n"
                        + "Topic: " + recordMetadata.topic() + "\n"
                        + "Partition: " + recordMetadata.partition() + "\n"
                        + "Offset: " + recordMetadata.offset() + "\n"
                        + "Timestamp: " + recordMetadata.timestamp());

            } else {
                logger.error("Error while producing", e);
            }
        }
    }).get(); // add this to make things synchronous, but not sure that this is a good idea
}
    //to send data we need to do flush data
        producer.flush();
    //this is if we want to flush and close the producer
        producer.close();
    }
}
