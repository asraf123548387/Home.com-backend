package com.example.demo.controller.userController;

import com.example.demo.Service.HotelService;
import com.example.demo.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserHotelController {
    @Autowired
    public HotelService hotelService;
    @GetMapping("/hotelList")
    public List<Hotel> getHotelListByUser(){
        return hotelService.getAllHotels();
    }




}
