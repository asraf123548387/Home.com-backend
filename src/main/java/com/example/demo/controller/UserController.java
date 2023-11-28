package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
        @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepo repo;
    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       user.setRoles("ROLE_USER");
        repo.save(user);
        return new ResponseEntity<String>("User Saved", HttpStatus.OK);
    }


}
