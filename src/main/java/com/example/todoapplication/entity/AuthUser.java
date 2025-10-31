package com.example.todoapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Setter
@Getter
@Component
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
