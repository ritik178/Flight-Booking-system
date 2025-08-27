package com.ritik.flightreservationsystem.reservation.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ritik.flightreservationsystem.reservation.system.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
	
	List<Flight> getFlightBySourceAndDestination(String source, String destination);

}
