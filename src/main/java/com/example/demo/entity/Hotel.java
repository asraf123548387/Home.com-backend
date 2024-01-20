package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String hotelName;
    private String address;
    private String phone;
    private String email;
    private String description;
    private double rating;
    private String location;
    private String images;
    @ManyToOne
    @JoinColumn(name="adminUserId")
    private User adminUser;
    @OneToMany(mappedBy = "hotel",cascade =CascadeType.ALL,fetch =FetchType.LAZY)
    private List<Room> rooms;
}
