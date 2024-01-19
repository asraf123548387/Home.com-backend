package com.example.demo.repo;

import com.example.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room,Long> {
}
