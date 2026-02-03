package com.uber.uberapi.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

     @GetMapping("/")
    public String home() {
        return "API do Supermercado está funcionando 🚀";
    }

}
