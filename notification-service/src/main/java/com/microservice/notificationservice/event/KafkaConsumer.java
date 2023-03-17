package com.microservice.notificationservice.event;

import com.microservice.notificationservice.dto.EmailNotification;
import com.microservice.notificationservice.service.MailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MailService mailService;

    @KafkaListener(topics = "notificationTopic")
    public void listen(@Payload OrderPlacedEvent orderPlacedEvent) throws Exception {
        mailService.sendMail(EmailNotification.builder()
                .subject("New Book Issued")
                .recipient("m.helal.k@gmail.com")
                .body("Received Notification for Order - "+ orderPlacedEvent.getOrderNumber())
                .build());


        log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());
    }
}
