package com.example.demo.controller.admincontroller;

import com.example.demo.Service.UserDetailsInfoService;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    UserDetailsInfoService userDetailsInfoService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepo repo;



    @GetMapping("/users")
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

@PostMapping("/block")
    public ResponseEntity<String> blockUser(@RequestBody Map<String,Long> request){
        long userId=request.get("userId");
    if (request.containsKey("userId")) {
        userDetailsInfoService.blockUser(userId);
        return ResponseEntity.ok("User blocked successfully");
    } else {
        return ResponseEntity.badRequest().body("Invalid request body");
    }
}
@PostMapping("/unblock")
    public ResponseEntity<String> unblockUser(@RequestBody Map<String, Long> request) {
        if (request.containsKey("userId")) {
            long userId = request.get("userId");
            userDetailsInfoService.unblockUser(userId);
            return ResponseEntity.ok("User blocked successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid request body");
        }
    }






//    @GetMapping("/adminUpdateUsers/{userId}")
//    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
//        {
//            Optional<User> userOptional = repo.findById(userId);
//            return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//        }
//
//    }


//    @PutMapping("/adminUpdateUsers/{userId}")
//    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
//        Optional<User> userOptional = repo.findById(userId);
//
//        if (userOptional.isPresent()) {
//            User existingUser = userOptional.get();
//            existingUser.setUserName(updatedUser.getUserName());
//            existingUser.setEmail(updatedUser.getEmail());
//            existingUser.setMobile(updatedUser.getMobile());
//
//            // Save the updated user
//            User savedUser = repo.save(existingUser);
//            return ResponseEntity.ok(savedUser);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/currentUser")
//    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
//        String username = userDetails.getUsername();
//        Optional<User> userOptional = repo.findByUserName(username);
//
//        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }




}