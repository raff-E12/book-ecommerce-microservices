package com.book.rate.errors;

import java.util.HashMap;

public class ErrorGlobal {
	
	private int status;
	private String messagge;
	private HashMap<String, Boolean> required = new HashMap<>();

	public int getStatus() {
		return status;
	}

	public HashMap<String, Boolean> getRequired() {
		return this.required;
	}

	public void setRequired(HashMap<String, Boolean> data) {
		this.required = data;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessagge() {
		return messagge;
	}
	
	public void setMessagge(String msg) {
		this.messagge = msg;
	}
}
