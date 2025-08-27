package com.ritik.flightreservationsystem.reservation.system.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "flight"})
public class Booking {
     
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int seatBooked;
	
	@Column(name = "total_price")
	private double totalPrice;
	
	private LocalDateTime bookingTime;
	
	private boolean cancelled = false;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties("bookings")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "flight_id")
	@JsonIgnoreProperties("bookings")
	private Flight flight;
	
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
	private List<Passenger> passengers;
}
