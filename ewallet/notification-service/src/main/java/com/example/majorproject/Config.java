package com.example.majorproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Configuration
public class Config {

    // Kafka common Config
    @Bean
    Properties kafkaProps() {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }

    // Kafka Consumer config

    @Bean
    ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory(kafkaProps());
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        return kafkaListenerContainerFactory;
    }

    // Email related config

    @Bean
    JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("gct.it18.dharani@gmail.com");
        javaMailSender.setPassword("kfvreupmpojjwrmx");

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        return javaMailSender;
    }

    @Bean
    SimpleMailMessage simpleMailMessage() {
        return new SimpleMailMessage();
    }


    // Other Config
    @Bean
    ObjectMapper getMapper() {
        return new ObjectMapper();
    }

}
