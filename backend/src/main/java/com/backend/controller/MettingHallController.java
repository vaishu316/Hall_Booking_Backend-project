package com.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.MeetingHalls;
import com.backend.security.JwtUtil;
import com.backend.services.MeetingHallService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/meettinghall")
@CrossOrigin(origins = "*")
public class MettingHallController {
	
	private final MeetingHallService meetingHallService;
	private final JwtUtil jwtUtil;
	
	public MettingHallController(MeetingHallService meetingHallService,JwtUtil jwtUtil) {
		this.meetingHallService=meetingHallService;
		this.jwtUtil=jwtUtil;
	}
	
	
	@GetMapping("/allhalls")
	public ResponseEntity<?> getAllHalls(HttpServletRequest request){
		
		 return ResponseEntity.ok(meetingHallService.getAllHalls());
	}
	
	@PostMapping("/addhall")
	public ResponseEntity<?> addHall(@RequestBody MeetingHalls meetingHalls){
		System.out.println("Addd hall requestcomming");
		if(meetingHallService.addHall(meetingHalls)!=null) {
			return ResponseEntity.ok("Hall Added Sucessfully");
		}
		return ResponseEntity.ok("Hall Not Added");
		
	}
	
	@DeleteMapping("/delete-hall")
	public ResponseEntity<?> deleteHall(@RequestBody MeetingHalls meetingHalls){
		
		try {
			meetingHallService.deleteHall(meetingHalls.getId());
			return ResponseEntity.ok("Hall Deleted Sucessfully");
		}
		catch (RuntimeException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something Wrong.Plase Try Again");
		}
	}

}
