package com.assignment.notification.NotificationProducer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.producer.bootstrap-servers}") private String kafkaBootstrapServer;
    @Bean
    public ProducerFactory<String, String> producerFactory(){
        try{
            Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaBootstrapServer);
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }
        catch (Exception e)
        {
            log.error("Error in kafka server. Error message: " + e.getMessage());
            throw e;
        }
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate()
    {
        try {
            return new KafkaTemplate<>(producerFactory());
        }
        catch (Exception e)
        {
            log.error("Error in kafka server. Error message: " + e.getMessage());
            throw e;
        }
    }
}
