package com.SpringSecurity.SpringSecurityApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
	
//	/* @RequestMapping("/csrf-token") */
//	public CsrfToken getCsrfToken(HttpServletRequest request) {
//		return (CsrfToken) request.getAttribute("_csrf");
//	}
	
	@RequestMapping("/")
	public String home(HttpServletRequest request) {
		return "welcome to page";
	}
	
	@RequestMapping("/products")
	public String products(HttpServletRequest request) {
		return "welcome to Products page";
	}
	
}
