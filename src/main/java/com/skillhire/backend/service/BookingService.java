package com.skillhire.backend.service;

import com.skillhire.backend.dto.booking.BookingRequest;
import com.skillhire.backend.dto.booking.BookingResponse;
import com.skillhire.backend.entity.Booking;
import com.skillhire.backend.entity.User;
import com.skillhire.backend.entity.WorkerProfile;
import com.skillhire.backend.enums.BookingStatus;
import com.skillhire.backend.exception.ResourceNotFoundException;
import com.skillhire.backend.repository.BookingRepository;
import com.skillhire.backend.repository.UserRepository;
import com.skillhire.backend.repository.WorkerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final WorkerProfileRepository workerProfileRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Transactional
    public BookingResponse create(BookingRequest req) {
        WorkerProfile worker = workerProfileRepository.findById(req.workerId())
                .orElseThrow(() -> new ResourceNotFoundException("Worker not found: " + req.workerId()));
        User customer = userRepository.findById(req.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + req.customerId()));

        Booking booking = Booking.builder()
                .worker(worker)
                .customer(customer)
                .category(worker.getCategory())
                .date(req.parsedDate())
                .time(req.parsedTime())
                .address(req.address())
                .description(req.description())
                .status(BookingStatus.PENDING)
                .price(worker.getPrice())
                .build();

        return mapper.toBookingResponse(bookingRepository.save(booking));
    }

    public List<BookingResponse> getForCustomer(Long customerId) {
        return bookingRepository.findByCustomer_IdOrderByDateDescTimeDesc(customerId)
                .stream().map(mapper::toBookingResponse).toList();
    }

    public List<BookingResponse> getForWorker(Long workerId) {
        return bookingRepository.findByWorker_IdOrderByDateDescTimeDesc(workerId)
                .stream().map(mapper::toBookingResponse).toList();
    }

    @Transactional
    public BookingResponse updateStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + bookingId));
        booking.setStatus(status);
        return mapper.toBookingResponse(bookingRepository.save(booking));
    }
}
