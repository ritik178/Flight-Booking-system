package com.ritik.flightreservationsystem.reservation.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ritik.flightreservationsystem.reservation.system.model.Booking;


public interface BookingRepository extends JpaRepository<Booking, Long> {

}
