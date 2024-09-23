package com.SpringSecurity.SpringSecurityApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.SpringSecurity.SpringSecurityApp.model.Role;
import com.SpringSecurity.SpringSecurityApp.model.UsersModel;
import com.SpringSecurity.SpringSecurityApp.repo.UserRepo;
import com.SpringSecurity.SpringSecurityApp.repo.RoleRepo;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Authenticate and verify user
    public ResponseEntity<String> verifyUser(UsersModel user) {
        try {
        	Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

                if (authentication.isAuthenticated()) {
                	String authToken=jwtService.getTokenKey(user.getUsername());
                	System.out.println(authToken);
                    return ResponseEntity.status(HttpStatus.OK).body(authToken)  ;
                } else {
                    throw new UsernameNotFoundException("Invalid user credentials");
                }
        }
        catch(UsernameNotFoundException e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }

    // Register a new user with a role
    @Transactional
    public ResponseEntity<String> register(UsersModel user, String roleName) {
    	
    	UsersModel existingUser=userRepo.findByUsername(user.getUsername());
    	UsersModel existingMobileNo=userRepo.findByMobileNo(user.getMobileNo());
    	if(existingUser !=null) {
    		 return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already exists");
    	}
    	
    	 if (existingMobileNo != null) {
    	        return ResponseEntity.status(HttpStatus.CONFLICT)
    	                .body("Mobile number already registered");
    	    }
    	
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Fetch the role from the database and assign it to the user
        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        user.setRoles(Set.of(role));

        // Save the user with the assigned role
        userRepo.save(user);

        // Return a success response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("welcome "+user.getUsername()+" check your mail and verify your account");
    }
}
