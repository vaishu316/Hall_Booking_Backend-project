package com.backend.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	private  String token="";
	
	public Key  getSiginKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	public String generateToken(String username) {
		Map<String, Object> claims= new HashMap<>();
		
		return createToken(claims,username);
	}
	
	public String createToken(Map<String, Object> claims,String subject) {	
		return token=Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(getSiginKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Claims extractAllClaims(String token) throws ExpiredJwtException{
		return Jwts.parserBuilder()
				.setSigningKey(getSiginKey())
				.build()
				 .parseClaimsJws(token)
				.getBody();
	}
	
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
	
	public boolean validateToken(String token,String username) {
		String extractedUsername=extractUsername(token);
		
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}
}
