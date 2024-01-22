package com.example.demo.controller.admincontroller;


import com.example.demo.Service.HotelService;
import com.example.demo.dto.HotelDTO;
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
    public ResponseEntity<?> getAllHotels(@RequestParam(name = "search", required = false) String search) {
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
             System.out.println(hotels);
            // Return the list of users
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            // Handle any exceptions, e.g., database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users");
        }
    }

@PostMapping("/savehotel")
public ResponseEntity<String> saveHotel(@RequestBody HotelDTO hotelDTO){

   Long userId= hotelDTO.getUserId();
   User adminUser=userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found with id: " + userId));
   Hotel hotel=new Hotel();
   hotel.setHotelName(hotelDTO.getHotelName());
   hotel.setAddress(hotelDTO.getAddress());
   hotel.setPhone(hotelDTO.getPhone());
   hotel.setLocation(hotelDTO.getLocation());
   hotel.setEmail(hotelDTO.getEmail());
   hotel.setDescription(hotelDTO.getDescription());
    hotel.setImages(hotelDTO.getImages());
    hotel.setAdminUser(adminUser);
    hotelRepo.save(hotel);
    return ResponseEntity.ok("Hotel added successfully");
}
}
