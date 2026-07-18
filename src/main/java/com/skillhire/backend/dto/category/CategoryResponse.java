package com.skillhire.backend.dto.category;

/** Shape matches the frontend's `Category` interface (icon is the Lucide icon name as a string). */
public record CategoryResponse(
        Long id,
        String slug,
        String name,
        String icon,
        String color,
        long workers
) {}
