package com.ritik.flightreservationsystem.reservation.system.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookingResponseDto {
	private Long bookingId;
    private String userName;
    private String userEmail;
    private String flightName;
    private String source;
    private String destination;
    private LocalDateTime bookingDate;
    private double totalPrice;
    private boolean cancelled;
    
    private List<PassengerDTO> passengers;

}
