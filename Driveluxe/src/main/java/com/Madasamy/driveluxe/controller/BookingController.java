package com.Madasamy.driveluxe.controller;

import com.Madasamy.driveluxe.dto.BookingDTO;
import com.Madasamy.driveluxe.model.Booking;
import com.Madasamy.driveluxe.model.Car;
import com.Madasamy.driveluxe.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
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
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-mm-yyyy");
        return bookings.stream().map(booking -> {
            Car car = booking.getCar();
            Integer carId = (car != null) ? car.getCarId() : null;

            String formattedDate=booking.getBookingDate().toLocalDate().toString();

            return new BookingDTO(
                    booking.getCustomerName(),
                    booking.getEmail(),
                    booking.getPhoneNumber(),
                    booking.getAddress(),
                    carId,
                    formattedDate,
                    booking.getImageUrl()
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



}
