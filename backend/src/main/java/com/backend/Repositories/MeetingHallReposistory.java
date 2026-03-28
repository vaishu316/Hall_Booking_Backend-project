package com.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.MeetingHalls;

public interface MeetingHallReposistory extends JpaRepository<MeetingHalls, Integer>{

}
