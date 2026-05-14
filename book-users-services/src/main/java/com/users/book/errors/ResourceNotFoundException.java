package com.users.book.errors;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;

    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(String msg) {
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
