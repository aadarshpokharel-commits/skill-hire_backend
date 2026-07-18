package com.skillhire.backend.dto.booking;

/** Shape matches the frontend's `Booking` interface in src/lib/mock-data.ts. */
public record BookingResponse(
        Long id,
        Long workerId,
        String workerName,
        String customerName,
        String category,
        String date,
        String time,
        String address,
        String description,
        String status,
        Integer price
) {}
