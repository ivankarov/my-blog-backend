package com.example.diplod.blog_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/debug")
public class DebugController {
    @GetMapping("/test-error")
    public String testError() throws Exception {
        throw new RuntimeException("test error message");
    }
    @GetMapping("/env")
    public String environmentInfo(){
        return "java.version: " + System.getProperty("java.version")+ "\nOS: "+System.getProperty("os.name");
    }
    @GetMapping("/health")
    public String healthCheck(){
        return "Application is health " + new Date();
    }
}
