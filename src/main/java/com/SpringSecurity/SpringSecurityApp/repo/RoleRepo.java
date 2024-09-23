package com.SpringSecurity.SpringSecurityApp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringSecurity.SpringSecurityApp.model.Role;
import com.SpringSecurity.SpringSecurityApp.model.UsersModel;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}