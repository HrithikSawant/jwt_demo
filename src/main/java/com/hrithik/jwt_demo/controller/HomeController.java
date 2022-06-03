package com.hrithik.jwt_demo.controller;

import com.hrithik.jwt_demo.entity.Message;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home/")
public class HomeController {
    @GetMapping("specific-url-pattern")
    public String home() {
        return "Welcome, Everyone!!";
    }

    @GetMapping("welcome")
    public String someMessage() {
        return "Welcome to JWT TOKEN!!";
    }

    @PostMapping("data")
    public Message save(@RequestBody Message message) {
        return new Message(message.getMessage());
    }

}
