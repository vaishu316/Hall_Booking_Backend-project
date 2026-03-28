package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.security.JwtUtil;

import io.jsonwebtoken.Claims;

@RestController
public class JwtController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@GetMapping("/generate-token")
	public String generateToken(@RequestParam String username) {
		return jwtUtil.generateToken(username);
	}
	
	@GetMapping("/getall-clams")
	public Claims getClaims(String token) {
		return jwtUtil.extractAllClaims("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpY"
				+ "XQiOjE3NDY0MjE5NzMsImV4cCI6MTc0NjQyNTU3M30.DmeeRlk8D3tPDZSAYJYuRJOiRnzFfnYc4hgiD0jyVJg");
	}
	
	@GetMapping("/username")
	public String username() {
		return jwtUtil.extractUsername("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpY\"\r\n"
				+ "				+ \"XQiOjE3NDY0MjE5NzMsImV4cCI6MTc0NjQyNTU3M30.DmeeRlk8D3tPDZSAYJYuRJOiRnzFfnYc4hgiD0jyVJg");
	}
	

}
