package com.Madasamy.driveluxe.controller;

import com.Madasamy.driveluxe.dto.BookingDTO;
import com.Madasamy.driveluxe.model.Booking;
import com.Madasamy.driveluxe.model.Car;
import com.Madasamy.driveluxe.service.BookingService;

import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//  Allows frontend to call backend
@CrossOrigin(origins = "http://127.0.0.1:5502")
@RequestMapping("/api/bookings")


public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping

    public List<BookingDTO> getAllBookings() {

        List<Booking> bookings = bookingService.getAllBookings();

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return bookings.stream().map(booking -> {

            Car car = booking.getCar();
            Integer carId = (car != null) ? car.getCarId() : null;

            String formattedDate=booking.getBookingDate().toLocalDate().toString();

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
    public String createBooking(@RequestBody BookingDTO bookingDTO) {
        bookingService.saveBooking(
                bookingDTO.getCarId(),
                bookingDTO.getCustomerName(),
                bookingDTO.getEmail(),
                bookingDTO.getPhoneNumber(),
                bookingDTO.getAddress()
        );
        return "Booking successful";
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
