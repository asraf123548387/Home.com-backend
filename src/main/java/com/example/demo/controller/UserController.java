package com.example.demo.controller;

import com.example.demo.Userservice.JwtService;
import com.example.demo.Userservice.UserDetailsInfoService;
import com.example.demo.dto.AuthRequest;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {
    @Autowired
    UserDetailsInfoService userDetailsInfoService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepo repo;


    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       user.setRoles("ROLE_USER");

        User user2 = repo.save(user);
        System.out.println(user2.getUserName());
        System.out.println(user2.getId());
        return new ResponseEntity<String>("User Saved", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                List<String> roles = userDetailsInfoService.getUserRoles(authRequest.getUsername());
                String token = jwtService.generateToken(authRequest.getUsername(),roles);

                System.out.print(token);
                return ResponseEntity.ok(token);
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }


}
