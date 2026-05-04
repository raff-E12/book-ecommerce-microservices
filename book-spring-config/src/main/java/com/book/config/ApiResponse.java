package com.book.config;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    
    public ApiResponse() {
        this.status = "SUCCESS";
        this.message = "Operazione completata";
        this.timestamp = LocalDateTime.now();
    }
    
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public T getData() { return data; }
    public void setData(T date) { this.data = date; }

    public LocalDateTime getTimeStamp() { return timestamp; }
    public void setTimeStamp(LocalDateTime local) { this.timestamp = local; }
}
