package com.Madasamy.driveluxe.service;

import com.Madasamy.driveluxe.model.Booking;
import com.Madasamy.driveluxe.model.Car;
import com.Madasamy.driveluxe.repository.BookingRepository;
import com.Madasamy.driveluxe.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;

    public BookingService(BookingRepository bookingRepository, CarRepository carRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository;
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void saveBooking(int carId, String customerName, String email, String phoneNumber,String address) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null) {
            String imageUrl = car.getImageUrl();
            if (imageUrl != null) {
                Booking booking = new Booking(car, customerName, email, phoneNumber, address, imageUrl);
                booking.setBookingStatus(Booking.BookingStatus.SUBMITTED);
                bookingRepository.save(booking);
                System.out.println("Booking saved with image URL: " + imageUrl);
            } else {
                System.out.println("Image URL is null for car ID: " + carId);
            }
        } else {
            System.out.println("Car not found with ID: " + carId);
        }
    }

    public Booking updateStatus(int id, Booking.BookingStatus status) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setBookingStatus(status);
            return bookingRepository.save(booking);
        }
        return null;
    }



    public Booking updateStatus(int id, Booking.BookingStatus status, String reason) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setBookingStatus(status);
            if (status == Booking.BookingStatus.CANCELLED && reason != null) {
                booking.setCancellationReason(reason);
            }
            return bookingRepository.save(booking);
        }
        return null;
    }














}
