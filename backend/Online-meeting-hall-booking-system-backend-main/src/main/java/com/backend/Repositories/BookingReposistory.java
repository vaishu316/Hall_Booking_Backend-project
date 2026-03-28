package com.backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.model.Booking;

import jakarta.transaction.Transactional;

public interface BookingReposistory extends JpaRepository<Booking, Integer>{
	
	@Query("SELECT b FROM Booking b " +
		       "WHERE b.meetingHalls.id = :hallId " + 
		       "AND b.bookingdate = :bookingDate " + 
		       "AND b.status = 'BOOKED' " + 
		       "AND (:startTime < b.endTime AND :endTime > b.startTime)")
		List<Booking> checkHallAvailability(
		    @Param("hallId") int hallId,
		    @Param("bookingDate") String bookingDate,
		    @Param("startTime") String startTime,
		    @Param("endTime") String endTime);



	
		List<Booking> findByUserId(int userid);
		
		@Modifying
		@Transactional
		@Query("UPDATE Booking b SET b.status = :status WHERE b.bookingId =:bookingId")
		int updateBookingStatus(@Param("bookingId") int bookinid,@Param("status") String status);
		
		List<Booking> findByUserIdOrderByBookingIdDesc(int userId);

	
}
