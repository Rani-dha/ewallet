package com.example.majorproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    SimpleMailMessage simpleMailMessage;


    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = {"send_email"}, groupId = "test1234")
    public void sendMail(String message) throws JsonProcessingException {


        JSONObject emailRequest = objectMapper.readValue(message, JSONObject.class);

//        MimeMessage mimeMessage;
//        MimeMessageHelper mimeMessageHelper;
//        mimeMessageHelper.ser
//

        simpleMailMessage.setText((String) emailRequest.get("message"));
        simpleMailMessage.setTo((String) emailRequest.get("toUser"));
        simpleMailMessage.setSubject("Transaction Report");
        simpleMailMessage.setFrom("gct.it18.dharani@gmail.com");


//        System.out.println(" Notification: " + (String) emailRequest.get("message") + "  "
//                + (String) emailRequest.get("toUser") + " 2 ");

        javaMailSender.send(simpleMailMessage);
    }
}
