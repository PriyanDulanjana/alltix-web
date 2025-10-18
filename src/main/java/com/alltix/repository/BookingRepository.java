package com.alltix.repository;

import com.alltix.model.Booking;
import com.alltix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserOrderByBookingTimeDesc(User user);
    List<Booking> findAllByOrderByBookingTimeDesc();
    List<Booking> findByUser(User user);
}