package com.student.orders.components;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.student.orders.events.OrderCheck;
import com.student.orders.events.OrderCreated;
import com.student.orders.events.OrderFeedback;

@Component
// Gestione di invio Evento al Broker Kafka
public class OrderKafka{
    private final KafkaTemplate<String, OrderCheck> CheckTemplate;
    private final KafkaTemplate<String, OrderCreated> CreatedTemplate;
    private final KafkaTemplate<String, OrderFeedback> CreatedFeedback;

    public OrderKafka(
        KafkaTemplate<String, OrderCheck> check,
        KafkaTemplate<String, OrderCreated> create,
        KafkaTemplate<String, OrderFeedback> feedback
    ) {
        this.CheckTemplate = check;
        this.CreatedTemplate = create;
        this.CreatedFeedback = feedback;
    }

    public void sendOrderCompleted(OrderCheck event) {
        CheckTemplate.send("order", event);
    }

    public void sendOrderCreated(OrderCreated event) {
        CreatedTemplate.send("created", event);
    }

    public void sendFeedBack(OrderFeedback event) {
        CreatedFeedback.send("feedback", event);
    }
}
