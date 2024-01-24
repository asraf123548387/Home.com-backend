package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(length = 1000)
    private String description;
    private double rating;
    private String location;
    private String images;
    private String price;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="adminUserId")
    private User adminUser;
    @JsonManagedReference
    @OneToMany(mappedBy = "hotel",cascade =CascadeType.ALL,fetch =FetchType.LAZY)
    private List<Room> rooms;
}
