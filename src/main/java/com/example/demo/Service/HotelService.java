package com.example.demo.Service;

import com.example.demo.entity.Hotel;
import com.example.demo.repo.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    HotelRepo hotelRepo;

    public List<Hotel> getHotelBySearch(String search) {
        // Assuming your repository has a method like findByHotelNameContainingIgnoreCase
        // You can customize this based on your actual entity fields and search logic
        return hotelRepo.findByHotelNameContainingIgnoreCase(search);
    }
    public List<Hotel> getAllHotels() {

        return hotelRepo.findAll();
    }
}
