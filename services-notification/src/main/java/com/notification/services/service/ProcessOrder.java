package com.notification.services.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.notification.services.events.OrderCheck;
import com.notification.services.events.OrderCreated;
import com.notification.services.events.OrderFeedback;

// Estrare l'evento da essere usato come log, poi ai vari topic.
@Service
public class ProcessOrder {
    
    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    public void checkOrder(OrderCheck event) {
        log.info("Ordine Checkout → ID: {}", event.id());
        log.info("Totale: {}", event.totalPrice());
        log.info("Pagato: {}", event.isPaid());
    }

    public void createOrder(OrderCreated event) {
        log.info("Ordine Creato → ID: {}", event.id());
        log.info("Titolo: {}", event.BookName());
        log.info("Prezzo: {}", event.Price());
        log.info("Quantità: {}", event.Quantity());
        log.info("SottoTotale", event.SubTotal());
    }

    public void FeedbackApi(OrderFeedback event) {
        log.info("Priorità: {}", event.isPriority());
        log.info("Contesto: {}", event.isContext());
        log.info("Validità: {}", event.isValid());
    }

}
