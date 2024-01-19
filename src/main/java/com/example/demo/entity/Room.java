package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String roomNumber;
    private double pricePerNight;
    private boolean availability;
    @ManyToOne
    @JoinColumn(name="hotelId")
    private Hotel hotel;

    private String images;

}
