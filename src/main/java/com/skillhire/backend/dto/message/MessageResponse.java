package com.skillhire.backend.dto.message;

public record MessageResponse(
        Long id,
        Long senderId,
        Long receiverId,
        String content,
        String sentAt,
        Boolean read
) {}
