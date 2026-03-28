package com.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.Admin;

public interface AdminReposistory extends JpaRepository<Admin, Integer>{
	Admin findByEmail(String email);

}
