package com.book.rate.controller;


@RestController
@RequestMapping("/api-test")
public class ProvaController {
    
    @Autowired
    private OrdersServices services;
    private String messaggio = "Ciao a tutti, benvenuti in OrdersDespenceApi";

    @GetMapping("/welcome")
    public ResponseEntity<String> testWelcome(){
        return new ResponseEntity<String>("\""+messaggio+"\"", HttpStatus.OK);
    }
    
    @GetMapping("/db-test/{id}")
    public ResponseEntity<List<Checkout>> FindAll(@PathVariable int id){
        return new ResponseEntity<>(services.getAllOrders(id), HttpStatus.OK);
    }

}