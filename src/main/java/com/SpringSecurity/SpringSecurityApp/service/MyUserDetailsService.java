package com.SpringSecurity.SpringSecurityApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringSecurity.SpringSecurityApp.model.UserPrincipal;
import com.SpringSecurity.SpringSecurityApp.model.UsersModel;
import com.SpringSecurity.SpringSecurityApp.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo repoUser;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UsersModel user=repoUser.findByUsername(username);
		if(user==null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("User not found");
		}
		return new UserPrincipal(user);
		
	}







	

}
