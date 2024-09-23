package com.SpringSecurity.SpringSecurityApp.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    // Assuming you have users mapped here
    @JsonIgnore // Prevents serialization of the users in Role
    @ManyToMany(mappedBy = "roles")
    private Set<UsersModel> users;

    // Default constructor (required by JPA)
    public Role() {}

    // Constructor that accepts role name
    public Role(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UsersModel> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersModel> users) {
        this.users = users;
    }
}
