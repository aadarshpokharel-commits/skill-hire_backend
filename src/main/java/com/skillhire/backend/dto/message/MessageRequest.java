package com.skillhire.backend.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageRequest(
        @NotNull Long senderId,
        @NotNull Long receiverId,
        @NotBlank String content
) {}
