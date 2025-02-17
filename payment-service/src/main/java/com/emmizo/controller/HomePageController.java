package com.emmizo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {


    @GetMapping
    public String HomePageControllerHandler() {
        return "Payment service microservices";
    }
}
