package com.skillhire.backend.dto.worker;

import java.util.List;

/**
 * Shape matches the frontend's `Worker` interface in src/lib/mock-data.ts.
 * `initials` and `color` are derived server-side from the worker's name so the
 * frontend doesn't need any changes to its rendering logic.
 */
public record WorkerResponse(
        Long id,
        String name,
        String profession,
        String category,
        Double rating,
        Integer reviewsCount,
        Integer experience,
        Integer price,
        String location,
        List<String> languages,
        List<String> skills,
        String about,
        Boolean available,
        Integer completedJobs,
        Boolean verified,
        String initials,
        String color
) {}
