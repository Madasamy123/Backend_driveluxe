package com.Madasamy.driveluxe.dto;

import java.time.LocalDateTime;

public class BookingDTO {
    private String customerName;
    private String email;
    private String phoneNumber;
    private String address;
    private Integer carId;
    private String bookingDate;
    private String imageUrl;

    // Constructor to initialize all fields
    public BookingDTO(String customerName, String email, String phoneNumber,String address, Integer carId, String bookingDate, String imageUrl) {
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address=address;
        this.carId = carId;
        this.bookingDate = bookingDate;
        this.imageUrl = imageUrl;
    }


    // Getters for the fields (required for Spring to convert the object to JSON)
    public String getCustomerName() {
        return customerName;
    }


    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getCarId() {
        return carId;
    }

    public String getBookingDate() {
        return bookingDate;
    }



    public String getImageUrl() {
        return imageUrl;
    }


}
