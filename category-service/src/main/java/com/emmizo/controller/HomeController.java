package com.emmizo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String HomeControllerHandler() {
        return "Welcome to Category Service";
    }
}
