package com.hrithik.jwt_demo.service;

import com.hrithik.jwt_demo.entity.User;
import com.hrithik.jwt_demo.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private PasswordEncoder passwordEncoder;

    List<User> userList = Arrays.asList(
            new User(1L, "Patient", "stefan@gmail.com", "$2a$10$u.kYaD8H8gdGBcmKD6qW2eEeI7VN21ZMF9MzKzWzXwR.uWpAQIQYW"),
            new User(2L, "Receptionist", "ruby@gmail.com", "$2a$10$w6EpRCIgOoDkDyhyUpPzqurUMZ/bJv/2e/rc0hGIzuaQ2YMGmJ3mi"),
            new User(3L, "Admin","Admin@gmail.com", "$2a$10$JJDcuHcOLAfcVwRLIJJ.AeWncsndR6/XY8mJD8pP1WS0pgLnR05Hq"));


    public String authenticate(User authUser){
        Optional<User> dbUser = userList.stream()
                .filter(user -> user.getEmailId().equals(authUser.getEmailId())
                        &&  passwordEncoder.matches(authUser.getPassword(),user.getPassword()))
                .findFirst();
        if(dbUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return jwtUtility.generateToken(dbUser.get());
    }

    public User loadUserByUserId(Long id) {
        return userList.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }
}
