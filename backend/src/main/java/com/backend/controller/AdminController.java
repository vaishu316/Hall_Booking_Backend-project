package com.backend.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.Admin;
import com.backend.security.JwtUtil;
import com.backend.services.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {
	
	private final AdminService adminService;
	private final JwtUtil jwtUtil;
	
	public AdminController(AdminService adminService,JwtUtil jwtUtil) {
		this.adminService=adminService;
		this.jwtUtil=jwtUtil;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> adminLogin(@RequestBody Admin admin) {
	    Admin adminInDb = adminService.adminLogin(admin.getEmail());

	    if (adminInDb == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
	    }

	    if (!adminInDb.getPassword().equals(admin.getPassword())) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
	    }

	    String jwtToken = jwtUtil.generateToken(admin.getEmail());
	    return ResponseEntity.ok( jwtToken);
	}
	
	@GetMapping("/get-admin")
	public ResponseEntity<?> getAdminData(Principal principal){

		String currentUser=principal.getName();
	System.out.println(adminService.adminLogin(currentUser));
		return ResponseEntity.ok(adminService.adminLogin(currentUser));
	}
	
	
	@PutMapping("/update-password")
	public ResponseEntity<?> updatePassword(@RequestBody Admin admin) {
	    Admin dbAdmin = adminService.adminLogin(admin.getEmail());

	    if (dbAdmin == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Enter Valid Email");
	    }

	    dbAdmin.setPassword(admin.getPassword());
	   
	    return ResponseEntity.ok( adminService.saveAdmin(dbAdmin));
	}

	@PostMapping("/update")
	public ResponseEntity<String> updateAdmin(@RequestBody Admin admin) {
	    if (adminService.updateAdmin(admin) != null) {
	        return ResponseEntity.ok("Updated Successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
	    }
	}


}
