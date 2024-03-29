package com.example.demo.repo;

import com.example.demo.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HotelRepo extends JpaRepository<Hotel,Long> {


    List<Hotel> findByHotelNameContainingIgnoreCase(String search);
}
