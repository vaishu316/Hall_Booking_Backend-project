package com.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	private String bookingdate;
	private String startTime;
	private String endTime;
	private String status;
	
	@ManyToOne
	@JoinColumn(name="user_id")
//	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "meeting_hall_id")
//	@JsonIgnore
	private MeetingHalls meetingHalls;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingdate() {
		return bookingdate;
	}

	public void setBookingdate(String bookingdate) {
		this.bookingdate = bookingdate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MeetingHalls getMeetingHalls() {
		return meetingHalls;
	}

	public void setMeetingHalls(MeetingHalls meetingHalls) {
		this.meetingHalls = meetingHalls;
	}

	
	@Override
	public String toString() {
	    return "Booking [bookingId=" + bookingId + ", bookingdate=" + bookingdate +
	           ", startTime=" + startTime + ", endTime=" + endTime + ", status=" + status + "]";
	}


	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Booking(int bookingId, String bookingdate, String startTime, String endTime, String status, User user,
			MeetingHalls meetingHalls) {
		super();
		this.bookingId = bookingId;
		this.bookingdate = bookingdate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.user = user;
		this.meetingHalls = meetingHalls;
	}
	
	
	

}
