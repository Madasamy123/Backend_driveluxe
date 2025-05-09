package com.Madasamy.driveluxe.controller;

import com.Madasamy.driveluxe.Util.JwtUtil;
import com.Madasamy.driveluxe.dto.BookingDTO;
import com.Madasamy.driveluxe.model.Booking;
import com.Madasamy.driveluxe.model.Car;
import com.Madasamy.driveluxe.model.User;
import com.Madasamy.driveluxe.service.BookingService;
import com.Madasamy.driveluxe.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5502")
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<BookingDTO> getAllBookings() {

        List<Booking> bookings = bookingService.getAllBookings();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return bookings.stream().map(booking -> {
            Car car = booking.getCar();
            Integer carId = (car != null) ? car.getCarId() : null;

            String formattedDate = booking.getBookingDate().toLocalDate().toString();

            return new BookingDTO(
                    booking.getId(),
                    booking.getCustomerName(),
                    booking.getEmail(),
                    booking.getPhoneNumber(),
                    booking.getAddress(),
                    carId,
                    formattedDate,
                    booking.getImageUrl(),
                    booking.getBookingStatus() != null ? booking.getBookingStatus().name() : "SUBMITTED"
            );
        }).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<String> createBooking(
            @RequestBody BookingDTO bookingDTO,
            @RequestHeader("Authorization") String token) {

        // 🔐 Token Validation
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Unauthorized: No token provided");
        }

        token = token.substring(7); // Remove "Bearer " prefix

        // 🔎 Extract Email from Token
        String userEmail = jwtUtil.extractEmail(token);

        // 🔍 Verify User and Role
        Optional<User> userOptional = userService.findByEmail(userEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if ("USER".equals(user.getRole())) {
                // ✅ If user is valid and role is "USER", proceed with booking
                bookingService.saveBooking(
                        bookingDTO.getCarId(),
                        bookingDTO.getCustomerName(),
                        bookingDTO.getEmail(),
                        bookingDTO.getPhoneNumber(),
                        bookingDTO.getAddress()
                );
                return ResponseEntity.status(201).body("Booking successful!");
            } else {
                return ResponseEntity.status(403).body("Access Denied: Only users can book cars");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }


//    @PutMapping("/{id}/status")
//    public Booking updateBookingStatus(@PathVariable int id, @RequestParam Booking.BookingStatus status) {
//        return bookingService.updateStatus(id, status);
//    }

    @PutMapping("/{id}/status")
    public Booking updateBookingStatus(
            @PathVariable int id,
            @RequestParam Booking.BookingStatus status,
            @RequestBody(required = false) Map<String, String> requestBody) {

        String reason = null;
        if (requestBody != null) {
            reason = requestBody.get("reason");
        }

        return bookingService.updateStatus(id, status, reason);
    }
}

