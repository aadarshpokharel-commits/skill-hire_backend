package com.skillhire.backend.repository;

import com.skillhire.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer_IdOrderByDateDescTimeDesc(Long customerId);
    List<Booking> findByWorker_IdOrderByDateDescTimeDesc(Long workerId);
}
