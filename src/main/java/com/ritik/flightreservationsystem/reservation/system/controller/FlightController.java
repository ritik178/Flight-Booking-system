package com.ritik.flightreservationsystem.reservation.system.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ritik.flightreservationsystem.reservation.system.dto.FlightSearchRequest;
import com.ritik.flightreservationsystem.reservation.system.model.Flight;
import com.ritik.flightreservationsystem.reservation.system.model.Flight.FlightStatus;
import com.ritik.flightreservationsystem.reservation.system.service.FlightService;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	
	@PostMapping("/add")
	public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
	    Flight saved = flightService.addFlight(flight);
	    return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Flight>> getAllFlights(){
		List<Flight> result = flightService.getAllFlight();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		flightService.deleteFlight(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/get/{id}")
	public ResponseEntity<Flight> findFightById(@PathVariable Long id){
		Flight result = flightService.getFlightById(id);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight updated){
		Flight result = flightService.updateFlight(id, updated);
		return new ResponseEntity<>(result,HttpStatus.CREATED);
	}
	
	@PostMapping("/addInBulk")
	public ResponseEntity<List<Flight>> addInBulkFlight(@RequestBody List<Flight> flights){
		List<Flight> result = flightService.addInBulk(flights);
		return new ResponseEntity<List<Flight>>(result,HttpStatus.CREATED);
	}
	@GetMapping("/getbydate")
	public ResponseEntity<List<Flight>> getFlightBySourceAndDestination(@RequestBody FlightSearchRequest flight){
		List<Flight> flights = flightService.getFlightBySourceAndDestination(flight.getSource(), flight.getDestination(), flight.getDate());
		
		return new ResponseEntity<>(flights,HttpStatus.OK);
	}
	
	@PutMapping("/update/status/{id}")
	public ResponseEntity<String> updateFlightStatus(@PathVariable Long id, @RequestBody Map<String, String> body){
		
		FlightStatus status = FlightStatus.valueOf(body.get("status").toUpperCase());
		flightService.updateFlightStatus(id, status);
		return ResponseEntity.ok("status updated successfully"+ status);
		
	}

}
