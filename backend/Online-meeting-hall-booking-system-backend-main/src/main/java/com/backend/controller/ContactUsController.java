package com.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Repositories.ContactUsReposistory;
import com.backend.model.ConatchUs;

@RestController
@CrossOrigin(origins = "*")
public class ContactUsController {
	
	private final ContactUsReposistory contactUsReposistory;
	
	public ContactUsController(ContactUsReposistory contactUsReposistory) {
		this.contactUsReposistory=contactUsReposistory;
	}
	
	@PostMapping("/contact-us")
	public ResponseEntity<?> addContachDetails(@RequestBody ConatchUs conatchUs) {
		if(contactUsReposistory.save(conatchUs)!=null) {
			return ResponseEntity.ok("Message Submited Sucessfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
	}

}
