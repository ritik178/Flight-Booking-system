package com.ritik.flightreservationsystem.reservation.system.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookingRequestDto {

	private Long userId;
	private Long flightId;
	private int seatBooked;
	
	private List<PassengerDTO> passengers;
}
