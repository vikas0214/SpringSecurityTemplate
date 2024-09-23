package com.SpringSecurity.SpringSecurityApp.model;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{
	
	private UsersModel user;
	
	public UserPrincipal(UsersModel user) {
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// roles we are sending a static one if we maintain in the database then we can do it here
		return user.getRoles().stream()
	               .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
	               .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
