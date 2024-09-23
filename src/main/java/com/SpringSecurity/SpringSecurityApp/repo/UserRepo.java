package com.SpringSecurity.SpringSecurityApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringSecurity.SpringSecurityApp.model.UsersModel;

@Repository
public interface UserRepo extends JpaRepository<UsersModel,Integer>{

	UsersModel findByUsername(String username);
	UsersModel findByMobileNo(String mobileNo);

			 
			
}
