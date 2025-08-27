package com.ritik.flightreservationsystem.reservation.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ritik.flightreservationsystem.reservation.system.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long>{
	
	List<Passenger> findByBookingId(Long BookingId);

}
