package com.ritik.flightreservationsystem.reservation.system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ritik.flightreservationsystem.reservation.system.dto.BookingRequestDto;
import com.ritik.flightreservationsystem.reservation.system.dto.BookingResponseDto;
import com.ritik.flightreservationsystem.reservation.system.dto.PassengerDTO;
import com.ritik.flightreservationsystem.reservation.system.emailpdf.PdfGenerator;
import com.ritik.flightreservationsystem.reservation.system.model.Booking;
import com.ritik.flightreservationsystem.reservation.system.model.Flight;
import com.ritik.flightreservationsystem.reservation.system.model.Passenger;
import com.ritik.flightreservationsystem.reservation.system.model.User;
import com.ritik.flightreservationsystem.reservation.system.repository.BookingRepository;
import com.ritik.flightreservationsystem.reservation.system.repository.FlightRepository;
import com.ritik.flightreservationsystem.reservation.system.repository.PassengerRepository;
import com.ritik.flightreservationsystem.reservation.system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
	
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final FlightRepository flightRepository;
	private final EmailService emailService;
	private final PassengerRepository passengerRepository;
	
	
	public BookingResponseDto createBooking(BookingRequestDto dto) {
		User user = userRepository.findById(dto.getUserId())
				.orElseThrow(() -> new RuntimeException("user not found"));
		
		Flight flight = flightRepository.findById(dto.getFlightId())
				.orElseThrow(() -> new RuntimeException("flight not find"));
		
		if(flight.getSeatsAvailable() < dto.getSeatBooked()) {
			throw new RuntimeException("Not enough seats available _ ");
		}
		
		int numberOfPassenger = dto.getPassengers().size();
		double totalPrices = flight.getPrice() * numberOfPassenger;
		
		
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setFlight(flight);
		booking.setSeatBooked(dto.getSeatBooked());
		booking.setTotalPrice(totalPrices);
		booking.setBookingTime(LocalDateTime.now());
		
		//final ticket
		flight.setSeatsAvailable(flight.getSeatsAvailable() - numberOfPassenger);
		flightRepository.save(flight);
		
		bookingRepository.save(booking);
		
		List<Passenger> passengers = dto.getPassengers().stream()
				.map(pDto -> {
					Passenger p = new Passenger();
					p.setName(pDto.getName());
					p.setAge(pDto.getAge());
					p.setGender(Passenger.Genders.valueOf(pDto.getGender().toUpperCase()));
					p.setIdProofNumber(pDto.getIdProofNumber());
					p.setBooking(booking);
					return p;
					}).collect(Collectors.toList());
		
		passengerRepository.saveAll(passengers);
		
		//prepare ResponseDto
		List<PassengerDTO> passengerDtos = passengers.stream().map(p ->{
			PassengerDTO pdto = new PassengerDTO();
			pdto.setName(p.getName());
			pdto.setAge(p.getAge());
			pdto.setIdProofNumber(p.getIdProofNumber());
			pdto.setGender(p.getGender().name());
			return pdto;
		}).collect(Collectors.toList());
		
		BookingResponseDto responseDto = new BookingResponseDto(
		        booking.getId(),
		        user.getUsername(),
		        user.getEmail(),
		        flight.getAirline(),
		        flight.getSource(),
		        flight.getDestination(),
		        booking.getBookingTime(),
		        booking.getTotalPrice(),
		        booking.isCancelled(),
		        passengerDtos
		    );
		
		// PDF Generation & Email Send
        byte[] pdf = PdfGenerator.generateBookingPdf(responseDto);
        emailService.sendEmailWithAttachment(
                user.getEmail(),
                "Booking Confirmation - " + flight.getAirline(),
                "Please find attached your flight ticket.",
                pdf,
                "Ticket_Booking_" + booking.getId() + ".pdf"
        );

        return responseDto;
				
	}
	
	public List<BookingResponseDto> getAllBookings(){
		return bookingRepository.findAll().stream()
				.map(this::mapToDto).toList();
	}
	
	public BookingResponseDto getBookingById(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("booking not found with given id"+ id));
		return mapToDto(booking);
	}
	
	public void deleteBookingById(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Booking not found with given id"+ id));
		bookingRepository.delete(booking);
	}
	
	public String cancelBooking(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("booking not find for this id"));
		
		if(booking.isCancelled()) {
			return "booking is already cancelled";
		}
		
		booking.setCancelled(true);
		
		Flight flight = booking.getFlight();
		flight.setSeatsAvailable(flight.getSeatsAvailable()+booking.getSeatBooked());
		
		flightRepository.save(flight);
		bookingRepository.save(booking);
		
		return "booking cancelled Succesfully. Refund amount 0f Rs: " + booking.getTotalPrice() + "is processed. ";
	}
	private BookingResponseDto mapToDto(Booking booking) {
		return BookingResponseDto.builder()
				.bookingId(booking.getId())
				.userName(booking.getUser().getUsername())
				.userEmail(booking.getUser().getEmail())
				.flightName(booking.getFlight().getAirline())
				.source(booking.getFlight().getSource())
				.destination(booking.getFlight().getDestination())
				.bookingDate(booking.getBookingTime())
				.totalPrice(booking.getTotalPrice())
				.cancelled(booking.isCancelled())
				.build();
	}
	
	

}
