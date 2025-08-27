package com.ritik.flightreservationsystem.reservation.system.service;

import java.time.LocalDate;
import java.util.List;

import com.ritik.flightreservationsystem.reservation.system.model.Flight;
import com.ritik.flightreservationsystem.reservation.system.model.Flight.FlightStatus;

public interface FlightService {
	
   Flight addFlight(Flight flight);
   List<Flight> getAllFlight();
   void deleteFlight(Long id);
   Flight getFlightById(Long id);
   Flight updateFlight(Long id, Flight updated);
   List<Flight> addInBulk(List<Flight> flights);
   
   List<Flight> getFlightBySourceAndDestination(String source, String destination, LocalDate date);
   
   void updateFlightStatus(Long id,FlightStatus status);
}
