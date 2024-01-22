package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String userName;

        private String password;

        private String mobile;
        private String otp;
        private String email;
        private String roles;
        private boolean verified;
        private boolean isBlocked;
        @JsonManagedReference
        @OneToMany(mappedBy = "adminUser",cascade =CascadeType.ALL,fetch =FetchType.LAZY)
        private List<Hotel> adminHotels;
    }

