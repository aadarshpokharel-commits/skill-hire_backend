package com.skillhire.backend.dto.auth;

import com.skillhire.backend.enums.Role;

/** Shape matches the frontend's `User` interface in src/lib/auth.ts. */
public record AuthResponse(
        Long id,
        String name,
        String email,
        Role role
) {}
