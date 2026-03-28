package com.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.Repositories.MeetingHallReposistory;
import com.backend.model.MeetingHalls;

@Service
public class MeetingHallService {
	private final MeetingHallReposistory meetingHallReposistory;
	public MeetingHallService(MeetingHallReposistory meetingHallReposistory) {
		this.meetingHallReposistory=meetingHallReposistory;
	}
	
	public List<MeetingHalls> getAllHalls() {
		return meetingHallReposistory.findAll();
	}
	public MeetingHalls addHall(MeetingHalls meetingHalls) {
		meetingHalls.setAvailable(true);
		return meetingHallReposistory.save(meetingHalls);
	}
	
	public void deleteHall(int id) {
		if(!meetingHallReposistory.existsById(id)) {
			throw new RuntimeException("Some Thing Went Wrong");
		}
		meetingHallReposistory.deleteById(id);
	}
}
