package com.SpringSecurity.SpringSecurityApp.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SpringSecurity.SpringSecurityApp.model.Role;

import jakarta.annotation.PostConstruct;
import java.util.Optional;

@Component
public class RoleInitializationService {

    @Autowired
    private RoleRepo roleRepo;

    @PostConstruct
    public void initializeRoles() {
        // Check if roles already exist, and if not, create them
        if (!roleRepo.findByName("ADMIN").isPresent()) {
            roleRepo.save(new Role("ADMIN"));
        }
        if (!roleRepo.findByName("USER").isPresent()) {
            roleRepo.save(new Role("USER"));
        }
    }
}
