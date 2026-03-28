package com.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Repositories.BookingReposistory;
import com.backend.model.Booking;

@Service
public class BookingService {
	
	private final BookingReposistory bookingReposistory;
	@Autowired
	public BookingService(BookingReposistory bookingReposistory) {
		this.bookingReposistory=bookingReposistory;
	}
	
	public List<Booking> isHallavailable(Booking booking){
		  return bookingReposistory.checkHallAvailability(
			        booking.getMeetingHalls().getId(),
			        booking.getBookingdate(),
			        booking.getStartTime(),
			        booking.getEndTime()
			    );
	}
	
	public Booking addBooking(Booking booking) {
		List<Booking>  exisstingBookings=isHallavailable(booking);
		if(exisstingBookings.isEmpty()){
		booking.setStatus("BOOKED");
		return bookingReposistory.save(booking);
		}
		return null;
	}
	
	public List<Booking> allBookings(){
		return bookingReposistory.findAll();
	}
	
	public List<Booking> getBookingDetails(int userId){
		return bookingReposistory.findByUserIdOrderByBookingIdDesc(userId);
	}
	
	public int cancelBooking(Booking booking) {
		booking.setStatus("CANCELLED");
		return bookingReposistory.updateBookingStatus(booking.getBookingId(),booking.getStatus());
		
	}
	public Booking updateBooking(int id, Booking updatedBooking) {
	    Optional<Booking> optionalBooking = bookingReposistory.findById(id);

	    if (optionalBooking.isPresent()) {
	        Booking existingBooking = optionalBooking.get();
	        
	        existingBooking.setBookingdate(updatedBooking.getBookingdate());
	        existingBooking.setStartTime(updatedBooking.getStartTime());
	        existingBooking.setEndTime(updatedBooking.getEndTime());

	        return bookingReposistory.save(existingBooking);
	    } else {
	        throw new RuntimeException("Booking not found ");
	    }
	}


}
