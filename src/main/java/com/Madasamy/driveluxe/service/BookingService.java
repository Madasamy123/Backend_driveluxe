package com.madasamy.driveluxe.service;

import com.madasamy.driveluxe.model.Booking;
import com.madasamy.driveluxe.model.Car;
import com.madasamy.driveluxe.model.User;
import com.madasamy.driveluxe.repository.BookingRepository;
import com.madasamy.driveluxe.repository.DriveluxeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DriveluxeRepository driveluxeRepository;

    public BookingService(BookingRepository bookingRepository, DriveluxeRepository driveluxeRepository) {
        this.bookingRepository = bookingRepository;
        this.driveluxeRepository= driveluxeRepository;
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }




    // car booking

    public void saveBooking(int carId, String customerName, String email, String phoneNumber, String address, User user) {
        Car car = driveluxeRepository.findById(carId).orElse(null);
        if (car != null) {
            String imageUrl = car.getImageUrl();
            if (imageUrl != null) {
                //  Add the missing `User` object to the constructor
                Booking booking = new Booking(car, user, customerName, email, phoneNumber, address, imageUrl);
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


    //  update booking status

//    public Booking updateStatus(int id, Booking.BookingStatus status) {
//        Optional<Booking> optionalBooking = bookingRepository.findById(id);
//        if (optionalBooking.isPresent()) {
//            Booking booking = optionalBooking.get();
//            booking.setBookingStatus(status);
//            return bookingRepository.save(booking);
//        }
//        return null;
//    }



    public List<Booking> getUserBookings(User user) {
        return bookingRepository.findByUser(user);  // ➡️ Fetch only bookings by the specific user
    }





    // cancellation

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
