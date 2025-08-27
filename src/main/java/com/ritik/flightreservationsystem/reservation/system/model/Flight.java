package com.ritik.flightreservationsystem.reservation.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String airline;
	private String source;
	private String destination;

	private LocalDateTime arrivalTime;
	private LocalDateTime departureTime;
	
	private int seatsAvailable;
	private double price;
	
	public enum FlightStatus {
		ON_TIME,
		DELAYED,
		CANCELLED
	}
	
	private FlightStatus status = FlightStatus.ON_TIME;
	
	@OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Booking> bookings;

}
