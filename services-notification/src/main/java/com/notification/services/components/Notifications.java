package com.notification.services.components;

import org.apache.kafka.clients.producer.Producer;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.notification.services.events.OrderCheck;
import com.notification.services.events.OrderCreated;
import com.notification.services.events.OrderFeedback;
import com.notification.services.service.ProcessOrder;

@Component
// Cattura l'evento da order-services segnato da un gruppo di riferimento su qui i vari topic vengono creati.
@KafkaListener(topics = "orders", groupId = "notification-group")
public class Notifications {

    private final ProcessOrder notificationService;

    public Notifications(ProcessOrder notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaHandler
    public void listenCheck(OrderCheck event) {
        notificationService.checkOrder(event);
    }

    @KafkaHandler
    public void listenCreate(OrderCreated event) {
        notificationService.createOrder(event);
    }

    @KafkaHandler
    public void listenFeedBack(OrderFeedback event) {
        notificationService.FeedbackApi(event);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Evento sconosciuto ricevuto: " + object);
    }

}