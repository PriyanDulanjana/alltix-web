package com.alltix.service;

import com.alltix.model.Booking;
import com.alltix.model.User;
import com.alltix.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getBookingsByUser(User user) {
        // 🚨 IMPLEMENTATION 🚨
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByBookingTimeDesc();
    }

    public List<Booking> getUserBookings(User user) {
        return bookingRepository.findByUserOrderByBookingTimeDesc(user);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}