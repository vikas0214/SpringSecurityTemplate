package com.SpringSecurity.SpringSecurityApp.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
		private String secretKey="";
		
		public JWTService() {
			super();
			try {
				KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
				SecretKey sk =keyGen.generateKey();
				secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	public String getTokenKey(String username) {		
		Map <String,Object> claims=new HashMap<>();
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+60*60*60))
				.and()
				.signWith(getSecretKey())
				.compact();
		
	}

	private SecretKey getSecretKey() {
		byte [] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);	
	}
	
	// validate each resource or request using jwt token 
		public boolean validateToken(String token,UserDetails userDetail) {
		final String username=extractUsername(token);
		return (username.equals(userDetail.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	public String extractUsername(String token) {	
		return extractClaim(token,Claims::getSubject);	
	}
	
	private <T> T extractClaim(String token, Function <Claims,T> claimResolver ){
		final Claims claim=extractAllClaims(token);
		return claimResolver.apply(claim);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	

	 
}
