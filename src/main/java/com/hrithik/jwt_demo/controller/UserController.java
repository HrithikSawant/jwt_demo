package com.hrithik.jwt_demo.controller;


import com.hrithik.jwt_demo.entity.JwtResponse;
import com.hrithik.jwt_demo.entity.Message;
import com.hrithik.jwt_demo.entity.User;
import com.hrithik.jwt_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody User user) {
        return new JwtResponse(userService.authenticate(user));
    }

}

