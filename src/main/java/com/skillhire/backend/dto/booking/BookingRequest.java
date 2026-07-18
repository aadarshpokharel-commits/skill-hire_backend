package com.skillhire.backend.dto.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingRequest(
        @NotNull Long workerId,
        @NotNull Long customerId,
        @NotBlank String date,   // "yyyy-MM-dd" - matches frontend's mockBookings.date format
        @NotBlank String time,   // "HH:mm"
        @NotBlank String address,
        String description
) {
    public LocalDate parsedDate() {
        return LocalDate.parse(date);
    }

    public LocalTime parsedTime() {
        return LocalTime.parse(time);
    }
}
