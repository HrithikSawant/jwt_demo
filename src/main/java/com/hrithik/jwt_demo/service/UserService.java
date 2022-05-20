package com.hrithik.jwt_demo.service;

import com.hrithik.jwt_demo.entity.User;
import com.hrithik.jwt_demo.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JwtUtility jwtUtility;

    ArrayList<User> userData() {
        User user1 = new User(1L, "Patient", "stefan@gmail.com", "$2a$10$8DE7KBJzswVYuBZ6c3S4XeX/ta6ke4qwPaVN57cMXMPCMjncRiK7C");
        User user2 = new User(2L, "Receptionist", "ruby@gmail.com", "$2a$10$pxYNNRvfqr.nMYKKVTjA3eseYLZsEs4iikvqUGuH2w8IkTNh7m48a");
        User user3 = new User(3L, "Admin","hrithik@gmail.com", "$2a$10$6bMOWa5zlhOuppY56pXr7e14NYeJYuYyeiEJiVjqMWW9PVwQicOjO");
        ArrayList<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }

    public String authenticate(User user) {
        Optional<User> userByEmail = userData().stream()
                .filter(u -> u.getEmailId().equals(user.getEmailId()) && u.getPassword().equals(user.getPassword())).findFirst();
        if (userByEmail.isPresent()) {
            return jwtUtility.generateToken(userByEmail.get());
        } else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public User loadUserByUserId(Long id) {
        Optional<User> db_user = userData().stream().filter(u -> u.getId().equals(id)).findFirst();
        return db_user.get();
    }
}
