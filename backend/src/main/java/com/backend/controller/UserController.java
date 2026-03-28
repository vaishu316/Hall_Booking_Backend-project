package com.backend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Repositories.UserReposistoy;
import com.backend.model.User;
import com.backend.security.JwtUtil;
import com.backend.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
	
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	public UserController(UserService userService,AuthenticationManager authenticationManager,JwtUtil jwtUtil,PasswordEncoder passwordEncoder) {
		this.userService=userService;
		this.authenticationManager=authenticationManager;
		this.jwtUtil=jwtUtil;
		this.passwordEncoder=passwordEncoder;
	}
	
	
	@GetMapping("/home")
	public String name() {
		return "Hello";
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> userregister(@RequestBody User user){
//		System.out.println(user.toString());
//		System.out.println("Register request");
		if(userService.finduser(user.getEmail()) !=null) {
			  return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Present");
		}
		if(userService.checkMobileNumber(user.getMobile())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Mobile Number Already Present");
		}
		if(userService.addUser(user) !=null)
		return ResponseEntity.ok("Register Sucessfull");
			
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User  user){
		
		User dbUser=userService.finduser(user.getEmail());
		
		if(dbUser==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email Not Found");
		}
		
		if(dbUser != null && passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
			String jwtToken=jwtUtil.generateToken(user.getEmail());
			return ResponseEntity.ok().body(jwtToken);

		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invaild Password");
		}
	}
	
	
	
	
	@GetMapping("/getuser")
	public ResponseEntity<?> userDetails(HttpServletRequest request){
		
		String authHeader=request.getHeader("Authorization");
		System.out.println(authHeader);
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
		String jwt=authHeader.substring(7);
			String email=jwtUtil.extractUsername(jwt);
		User user=userService.finduser(email);
		
		return ResponseEntity.ok(user);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please Login Again");
	}
	
	@GetMapping("/update")
	public void userUpdate(Principal principal){
		
		System.out.println("Current user "+principal.getName());
		
	}
	
	@GetMapping("allusers")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@DeleteMapping("/delete")
	public String deleteUser() {
		return "Your Account Deleted Sucessfully";
	}
}
