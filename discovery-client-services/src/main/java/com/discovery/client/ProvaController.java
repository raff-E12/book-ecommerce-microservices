package com.discovery.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;

@RequestMapping("/api")
public class ProvaController {

    private String messaggio;

    @GetMapping("/test-welcome")
	public ResponseEntity<String> testWelcome(@RequestBody String msg) {
        this.messaggio = msg;
		return new ResponseEntity<String>("\""+messaggio+"\"", HttpStatus.OK);
	}
}
