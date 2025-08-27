package com.ritik.flightreservationsystem.reservation.system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ritik.flightreservationsystem.reservation.system.model.Flight;
import com.ritik.flightreservationsystem.reservation.system.model.Flight.FlightStatus;
import com.ritik.flightreservationsystem.reservation.system.repository.FlightRepository;

@org.springframework.stereotype.Service
public class FlightServiceImpl implements FlightService {
	@Autowired
	private FlightRepository flightRepository;

	@Override
	public Flight addFlight(Flight flight) {
		// TODO Auto-generated method stub
		return flightRepository.save(flight);
	}

	@Override
	public List<Flight> getAllFlight() {
		return flightRepository.findAll();
	}

	@Override
	public void deleteFlight(Long id) {
		flightRepository.deleteById(id);
	}

	@Override
	public Flight getFlightById(Long id) {
		Flight result = flightRepository.findById(id).orElse(null);
		return result;
	}

	@Override
	public Flight updateFlight(Long id, Flight updated) {
		Flight existing = flightRepository.findById(id).orElse(null);
		existing.setAirline(updated.getAirline());
		existing.setArrivalTime(updated.getArrivalTime());
		existing.setDepartureTime(updated.getDepartureTime());
		existing.setDestination(updated.getDestination());
		existing.setPrice(updated.getPrice());
		existing.setSeatsAvailable(updated.getSeatsAvailable());
		existing.setSource(updated.getSource());
		return flightRepository.save(existing);
	}

	@Override
	public List<Flight> addInBulk(List<Flight> flights) {
		// TODO Auto-generated method stub
		return flightRepository.saveAll(flights);
	}

	@Override
	public List<Flight> getFlightBySourceAndDestination(String source, String destination, LocalDate date) {
		// TODO Auto-generated method stub
		List<Flight> flights = flightRepository.getFlightBySourceAndDestination(source, destination);
		
		if(date != null) {
			return flights.stream()
					.filter(flight -> (flight.getDepartureTime().toLocalDate().equals(date)))
					.collect(Collectors.toList());
		}
		return flights;
	}

	@Override
	public void updateFlightStatus(Long id, FlightStatus status) {
		
		Flight flight = flightRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("not find flight using this id"));
		
		flight.setStatus(status);
		flightRepository.save(flight);
		
	}

}
