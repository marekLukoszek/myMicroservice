package com.finture.mymicroservice;

import org.openapitools.api.SendEmailToQueueApi;
import org.openapitools.model.EmailRequest;
import org.openapitools.model.EmailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class EmailController implements SendEmailToQueueApi {

    Logger logger = LoggerFactory.getLogger(EmailController.class);
    @Autowired
    private SendEmailService sendEmailService;

    @PostMapping(path="/sendEmailToQueue", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailResponse> sendEmailToQueue(@RequestBody @Valid EmailRequest emailRequest) {
        EmailResponse emailResponse = null;
        try {
            sendEmailService.sendEmailToQueue(emailRequest);

            emailResponse = new EmailResponse();
            emailResponse.setTopic(emailRequest.getTopic());
            emailResponse.setStatus("Wiadomość wysłana na kolejkę");
            logger.info("Wysyłka Rabbit OK");
        } catch (Exception e) {
            logger.error("Nieudana wysyłka na kolejkę");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(emailResponse);
    }
}