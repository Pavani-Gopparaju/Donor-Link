package com.donarlink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String role;
    @OneToMany
    List<Donation> donation;


    public User() {

    }
    public User(String username, String password, String email, String phone, String address, String gender, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.role = role;
    }





}
