package com.example.diplod.blog_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/api/test")
    public String test(){
        return "security disabled, api is working " + new java.util.Date();
    }
    @GetMapping("/")
    public String home(){
        return "Blog api is running";
    }
}
