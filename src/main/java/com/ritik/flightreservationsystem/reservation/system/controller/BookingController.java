package com.ritik.flightreservationsystem.reservation.system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritik.flightreservationsystem.reservation.system.dto.BookingRequestDto;
import com.ritik.flightreservationsystem.reservation.system.dto.BookingResponseDto;
import com.ritik.flightreservationsystem.reservation.system.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
	
     private final BookingService bookingService;
     
     @PostMapping("/book")
     public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto dto){
    	 return new ResponseEntity<>(bookingService.createBooking(dto), HttpStatus.CREATED);
     }
     
     @GetMapping("/getAll")
     public ResponseEntity<List<BookingResponseDto>> getAllBooking(){
    	 return ResponseEntity.ok(bookingService.getAllBookings());
     }
     
     @GetMapping("/get/{id}")
     public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id){
    	 return ResponseEntity.ok(bookingService.getBookingById(id));
     }
     
     @DeleteMapping("/delete/{id}")
     public ResponseEntity<String> deleteBookingById(@PathVariable Long id){
    	 bookingService.deleteBookingById(id);
    	 return ResponseEntity.ok("booking deleted succesfully");
     }
     
     @PutMapping("/cancel/{bookingId}")
     public ResponseEntity<String> cancellBooking(@PathVariable Long bookingId){
    	 String massege = bookingService.cancelBooking(bookingId);
    	 return new ResponseEntity<>(massege,HttpStatus.OK);
     }
     
}
