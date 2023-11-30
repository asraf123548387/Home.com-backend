package com.example.demo.controller;

import com.example.demo.Userservice.UserDetailsInfoService;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class AdminController {
    @Autowired
    UserDetailsInfoService userDetailsInfoService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepo repo;



    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "search", required = false) String search) {
        try {
            List<User> users;

            // Check if a search query is present
            if (search != null && !search.isEmpty()) {
                // If a search parameter is present, filter users based on the search query
                users = userDetailsInfoService.getUsersBySearch(search);
            } else {
                // If no search parameter, fetch all users
                users = userDetailsInfoService.getAllUsers();
            }

            // Return the list of users
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            // Handle any exceptions, e.g., database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users");
        }
    }


    @DeleteMapping("/admin/usersDelete/{userId}")  // Use {userId} here
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userDetailsInfoService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

    @PostMapping("/adminSaveAddUser")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");

        User user2 = repo.save(user);
//        System.out.println(user2.getUserName());
//        System.out.println(user2.getId());
        return new ResponseEntity<String>("User Saved", HttpStatus.OK);
    }


    @GetMapping("/adminUpdateUsers/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        {
            Optional<User> userOptional = repo.findById(userId);
            return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

    }


    @PutMapping("/adminUpdateUsers/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        Optional<User> userOptional = repo.findById(userId);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setMobile(updatedUser.getMobile());

            // Save the updated user
            User savedUser = repo.save(existingUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Optional<User> userOptional = repo.findByUserName(username);

        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}