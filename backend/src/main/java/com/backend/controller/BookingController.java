package com.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Repositories.UserReposistoy;
import com.backend.model.Booking;
import com.backend.model.User;
import com.backend.services.BookingService;
import com.backend.services.UserService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {
	
	
	private final BookingService bookingService;
	private final UserReposistoy userReposistoy;
	
	public BookingController(BookingService bookingService,UserReposistoy userReposistoy) {
		this.bookingService=bookingService;
		this.userReposistoy=userReposistoy;
	}
	
	@PostMapping("/book")
	public ResponseEntity<String> bookHall(@RequestBody Booking booking) {
	    Booking createdBooking = bookingService.addBooking(booking);

	    if (createdBooking != null) {
	        return ResponseEntity.ok("Hall booked successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body("Hall is already booked at this time. Please select another slot.");
	    }
	}

	
	@GetMapping("/getallbookings")
	public ResponseEntity<?> getallbookings(){
		
		List<Booking> bookings=bookingService.allBookings();
		
		return ResponseEntity.ok(bookings);
	}
	
	@GetMapping("/my-bookings")
	public ResponseEntity<?> userBookings(Principal principal){
		System.out.println("Request comming");
	String  username=principal.getName();
	System.out.println("Username" + username);
	User user=userReposistoy.findByEmail(username);
	System.out.println(user);
	
	return ResponseEntity.ok(bookingService.getBookingDetails(user.getId()));
	}
	
	@PutMapping("/cancel-booking")
	public ResponseEntity<?> cancelBooking(@RequestBody Booking booking){
		System.out.println("Cancel Booking");
		System.out.println(booking);
		if(bookingService.cancelBooking(booking)!=0) {
		return ResponseEntity.ok("Canceled Sucessfully");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Try After Sometime");
	}
	
	@PutMapping("/update-booking/{id}")
	public ResponseEntity<?> editBooking(@PathVariable int id,@RequestBody Booking booking){
		try {
			Booking updatedBooking = bookingService.updateBooking(id, booking);
			  return ResponseEntity.ok(updatedBooking);
		}
		catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Booking not found");
        }
	}
}
