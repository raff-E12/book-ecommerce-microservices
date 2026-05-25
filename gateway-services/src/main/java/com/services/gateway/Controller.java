package com.services.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class Controller {
    private String messaggio = "Ciao a tutti, benvenuti in GateWayApi";

    @GetMapping("/welcome")
	public ResponseEntity<String> testWelcome(){
		return new ResponseEntity<String>("\""+messaggio+"\"", HttpStatus.OK);
	}
    
}
