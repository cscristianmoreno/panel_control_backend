package com.example.myapp.models;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter
    @Setter
    @Column(name="id")
    private int id;

    @Getter
    @Setter
    @Column(name="name")
    private String name;

    @Getter
    @Setter
    @Column(name="lastname")
    private String lastname;

    @Getter
    @Setter
    @Column(name="email", unique=true)
    private String email;

    @Getter
    @Setter
    @Column(name="password")
    private String password;

    @Getter
    @Setter
    @Column(name="age")
    private int age;

    @Getter
    @Setter
    @CreationTimestamp
    @Column(name="register")
    private String register;

    @Getter
    @Setter
    @CreationTimestamp
    @Column(name="lastsession")
    private String lastsession;
}
