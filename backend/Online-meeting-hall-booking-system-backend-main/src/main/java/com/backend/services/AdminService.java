package com.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.Repositories.AdminReposistory;
import com.backend.model.Admin;

@Service
public class AdminService {
	private final AdminReposistory adminReposistory;
	
	public AdminService(AdminReposistory adminReposistory) {
		this.adminReposistory=adminReposistory;
	}
	
	public Admin adminLogin(String email) {
		return adminReposistory.findByEmail(email);
	}
	
	
	public Admin updateAdmin(Admin admin) {
	    Optional<Admin> existingAdmin = adminReposistory.findById(admin.getId());

	    if (existingAdmin.isPresent()) {
	        Admin existing = existingAdmin.get();
	        existing.setName(admin.getName());
	        existing.setEmail(admin.getEmail()); 
	        existing.setPassword(admin.getPassword());
	        return adminReposistory.save(existing);
	    } else {
	        return null;
	    }
	}
	
	public Admin saveAdmin(Admin admin) {
	    return adminReposistory.save(admin);
	}


	


}
