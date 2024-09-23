package com.SpringSecurity.SpringSecurityApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringSecurity.SpringSecurityApp.model.UsersModel;
import com.SpringSecurity.SpringSecurityApp.service.UserService;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	private  UserService userService;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	
// login verification mapping here 
	@PostMapping("/login")
	public ResponseEntity<String> getLogin(@RequestBody UsersModel user) {
		System.out.println("reaching here");
		System.out.println(user);
		return userService.verifyUser(user);
	}

	
// register a user here
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UsersModel user) {
		System.out.println(user.getMobileNo());
//		// before saving to the user data change password to Bcrpyt and then store it use Bean private BCryptPasswordEncoder=
//		//new BCryptPasswordEncoder()
//		// we also need decoder from hash to plain while login 
//		// change the security Config with no password encoder to 
//		user.setPassword(encoder.encode(user.getPassword()));
//		hard code user creation
//		UsersModel user1 = new UsersModel();
//		user1.setUsername("adminUser");
//		user1.setPassword("adminPassword");

		// Register the user with the role "ADMIN"		
		 // Try to register the user
	  return  userService.register(user, "USER");
	}
	
}
