package com.example.demo.controller;

import com.example.demo.Userservice.UserDetailsInfoService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class AdminController {
    @Autowired
    UserDetailsInfoService userDetailsInfoService;

    @GetMapping("/admin/users")



    public ResponseEntity<?> getAllUsers(Authentication authentication) {

        {
            try {
                // Fetch all users from the database
                List<User> users = userDetailsInfoService.getAllUsers();

                // Return the list of users
                return ResponseEntity.ok(users);
            } catch (Exception e) {
                // Handle any exceptions, e.g., database errors
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users");
            }
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




}
