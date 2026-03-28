package com.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.Repositories.UserReposistoy;
import com.backend.model.User;

@Service
public class UserService {
	
	private final UserReposistoy userReposistoy;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserReposistoy userReposistoy,PasswordEncoder passwordEncoder) {
		
		
		this.userReposistoy=userReposistoy;
		this.passwordEncoder=passwordEncoder;
	}
	
	public User addUser(User user) {
		System.out.println(user.toString());
		
//		if(userReposistoy.findByEmail(user.getEmail())!=null) {
//			return null;
//		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("USER");
		return userReposistoy.save(user);
		
	}
	
	public User finduser(String email) {
		return userReposistoy.findByEmail(email);
	}
	
	public List<User> getAllUsers(){
		return userReposistoy.findAll();
	}
	
	public boolean checkMobileNumber(String mobile) {
		
		return userReposistoy.findByMobile(mobile).isPresent();
	}

}
