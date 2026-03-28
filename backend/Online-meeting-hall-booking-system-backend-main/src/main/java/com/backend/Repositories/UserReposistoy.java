package com.backend.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.User;

public interface UserReposistoy extends JpaRepository<User, Integer>{
	User findByEmail(String email);
	Optional<User> findByMobile(String mobile);
}
