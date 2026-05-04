package com.book.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    private String messaggio = "Ciao a tutti, benvenuti in ConfigServices";
	
	@Autowired
	private Environment environment;

    @GetMapping("/welcome")
	public ResponseEntity<String> testWelcome(){
		return new ResponseEntity<String>("\""+messaggio+"\"", HttpStatus.OK);
	}

	@GetMapping("/config/{app}/{profile}")
	public ResponseEntity<ApiResponse<String>> getConfig(@PathVariable String app, @PathVariable String profile) {
		String config = environment.getProperty("custom.property." + app + "." + profile, "NON_TROVATA");
        
        ApiResponse<String> response = new ApiResponse<>(
            "SUCCESS", 
            "Config test completato", 
            config
        );
        return ResponseEntity.ok(response);
	}

	@GetMapping("/health-all")
    public ResponseEntity<ApiResponse<Map<String, String>>> allHealth() {
        Map<String, String> health = Map.of(
            "config-server", "UP",
            "eureka", "UP",
            "gateway", "UP"
        );
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", null, health));
    }


}
