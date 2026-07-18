package com.skillhire.backend.dto.booking;

import com.skillhire.backend.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;

public record BookingStatusUpdateRequest(
        @NotNull BookingStatus status
) {}
