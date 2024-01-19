package com.example.demo.controller.admincontroller;


import com.example.demo.Service.HotelService;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.User;
import com.example.demo.repo.HotelRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminHotelController {
    @Autowired
    HotelService hotelService;
    @Autowired
    HotelRepo hotelRepo;
    @Autowired
    UserRepo userRepo;

    @GetMapping("/hotelList")
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "search", required = false) String search) {
        try {
            List<Hotel> hotels;

            // Check if a search query is present
            if (search != null && !search.isEmpty()) {
                // If a search parameter is present, filter users based on the search query
                hotels = hotelService.getHotelBySearch(search);
            } else {
                // If no search parameter, fetch all users
                hotels = hotelService.getAllHotels();
            }

            // Return the list of users
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            // Handle any exceptions, e.g., database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users");
        }
    }

@PostMapping("/savehotel")
public ResponseEntity<String> saveHotel(@RequestBody Hotel hotel, @RequestParam Long userId){

    try {
        // Retrieve the user by ID and set it to the hotel
        User adminUser = userRepo.findById(userId).orElse(null);
        if (adminUser == null) {
            // Handle the case when the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Set the adminUser for the hotel
        hotel.setAdminUser(adminUser);

        // Save the hotel
        hotelRepo.save(hotel);

        return ResponseEntity.ok("Hotel saved successfully");
    } catch (Exception e) {
        // Handle any exceptions that occurred during the request
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving hotel: " + e.getMessage());
    }
}
}
