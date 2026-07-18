package com.skillhire.backend.controller;

import com.skillhire.backend.dto.booking.BookingRequest;
import com.skillhire.backend.dto.booking.BookingResponse;
import com.skillhire.backend.dto.booking.BookingStatusUpdateRequest;
import com.skillhire.backend.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(req));
    }

    @GetMapping("/customer/{customerId}")
    public List<BookingResponse> forCustomer(@PathVariable Long customerId) {
        return bookingService.getForCustomer(customerId);
    }

    @GetMapping("/worker/{workerId}")
    public List<BookingResponse> forWorker(@PathVariable Long workerId) {
        return bookingService.getForWorker(workerId);
    }

    @PatchMapping("/{bookingId}/status")
    public BookingResponse updateStatus(@PathVariable Long bookingId,
                                         @Valid @RequestBody BookingStatusUpdateRequest req) {
        return bookingService.updateStatus(bookingId, req.status());
    }
}
