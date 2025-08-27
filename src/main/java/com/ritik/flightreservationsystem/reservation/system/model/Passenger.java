package com.ritik.flightreservationsystem.reservation.system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Passenger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private int age;
	public enum Genders{
		MALE,
		FEMALE
	}
	
	private Genders gender = Genders.MALE;
	private String idProofNumber;
	
	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;

}


