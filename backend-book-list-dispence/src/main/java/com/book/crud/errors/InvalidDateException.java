package com.book.crud.errors;

import java.time.LocalDate;

public class InvalidDateException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String message;

    public InvalidDateException(){
        super();
    }

    public InvalidDateException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessagge() {
		return this.message;
	}
	
	public void setMessagge(String messaggio) {
		this.message = messaggio;
	}

}