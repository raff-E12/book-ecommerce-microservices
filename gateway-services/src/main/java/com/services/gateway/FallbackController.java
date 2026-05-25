package com.services.gateway;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/fallback")
public class FallbackController {

    // Deve coincidere con wait-duration-in-open-state di ogni servizio
    private static final int ORDER_RETRY_AFTER    = 30;
    private static final int BOOK_RETRY_AFTER     = 10;
    private static final int RATING_RETRY_AFTER   = 10;

    @RequestMapping("/order")
    public ResponseEntity<Map<String, Object>> orderFallback(HttpServletRequest request) {
        return buildFallbackResponse("orderService", ORDER_RETRY_AFTER, request);
    }

    @RequestMapping("/book")
    public ResponseEntity<Map<String, Object>> bookFallback(HttpServletRequest request) {
        return buildFallbackResponse("bookService", BOOK_RETRY_AFTER, request);
    }

    @RequestMapping("/book-rating")
    public ResponseEntity<Map<String, Object>> bookRatingFallback(HttpServletRequest request) {
        return buildFallbackResponse("bookRatingService", RATING_RETRY_AFTER, request);
    }

    private ResponseEntity<Map<String, Object>> buildFallbackResponse(
            String serviceName, int retryAfter, HttpServletRequest request) {

        Throwable cause = (Throwable) request.getAttribute(
            "org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreakerExceptionAttr"
        );

        String reason = "service_unavailable";
        if (cause instanceof TimeoutException ||
            (cause != null && cause.getMessage() != null && cause.getMessage().contains("TimeLimiter"))) {
            reason = "timeout";
        } else if (cause != null && cause.getMessage() != null && cause.getMessage().contains("CircuitBreaker")) {
            reason = "circuit_open";
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 503);
        body.put("error", "Service Temporarily Unavailable");
        body.put("service", serviceName);
        body.put("reason", reason);
        body.put("retryAfterSeconds", retryAfter);
        body.put("timestamp", Instant.now().toString());

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .header(HttpHeaders.RETRY_AFTER, String.valueOf(retryAfter))
                .body(body);
    }
}