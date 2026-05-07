package com.book.rate.errors;

import java.util.ArrayList;
import java.util.HashMap;

public class DataInsertException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
    private HashMap<String, Boolean> required = new HashMap<>(); // Lista di Parametri Obbligatori

    public DataInsertException(String[] data){
        super();
    }

    public DataInsertException(String msg,  HashMap<String, Boolean> list) {
        super(msg);
        this.message = msg;
        this.required = list;
    }

     public DataInsertException(String msg) {
        super(msg);
        this.message = msg;
    }

    public DataInsertException(HashMap<String, Boolean> list) {
        this.required = list;
    }

    public String getMessagge() {
		return this.message;
	}

    public String SetRequired() {
		return this.required.toString();
	}

    public HashMap<String, Boolean> getRequired() {
		return this.required;
	}
	
	public void setMessagge(String messaggio) {
		this.message = messaggio;
	}
}
