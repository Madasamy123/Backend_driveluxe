package com.madasamy.driveluxe.repository;

import com.madasamy.driveluxe.model.Booking;
import com.madasamy.driveluxe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUser(User user); // ➡️ Fetches bookings of a specific user
}
