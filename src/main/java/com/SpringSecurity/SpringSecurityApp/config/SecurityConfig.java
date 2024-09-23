package com.SpringSecurity.SpringSecurityApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity    //it says dont go with the chain go with mine chain
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTFilter jwtFilter;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
//	here is the chain spring will follow 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception {
		return httpSec
                .csrf(customizer->customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/home", true)
//                        .permitAll())
//                .logout(logout -> logout
//                        .logoutUrl("/perform_logout")
//                        .logoutSuccessUrl("/login?logout=true")
//                        .deleteCookies("JSESSIONID").permitAll())
//                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
				
		
	}
	
	// ahrd code user name and password no verification from the database
//	@Bean
//	public UserDetailsService userDetailService() {
//	UserDetails user1=User
//						.withDefaultPasswordEncoder()
//						.username("vikas")
//						.password("123")
//						.roles("USER")
//						.build();
//	return new InMemoryUserDetailsManager(user1);
//	}
	
	
	// with the help of Provider we can verify from the database 
		@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean 
	public AuthenticationManager suthenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();	}
	

	
	}




