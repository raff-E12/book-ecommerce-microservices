package com.book.rate.errors;

public class BadRequestException extends RuntimeException {
   
    private static final long serialVersionUID = 1L;
    private String message;

    public BadRequestException(){
        super();
    }

    public BadRequestException(String msg) {
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
