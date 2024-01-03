package com.finture.mymicroservice;

import org.openapitools.model.EmailRequest;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
    private final RabbitTemplate rabbitTemplate;

    public SendEmailService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailToQueue(EmailRequest emailRequest) {
        Message message = MessageBuilder.withBody(emailRequest.getBody().getBytes())
                .setHeader("to", emailRequest.getTo())
                .setHeader("topic", emailRequest.getTopic())
                .build();

        String queueName = "Email_transfer";
        rabbitTemplate.send(queueName, message);
    }
}

