package com.Madasamy.driveluxe.repository;

import com.Madasamy.driveluxe.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
