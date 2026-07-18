package com.skillhire.backend.dto.worker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

/** Submitted by a logged-in WORKER user to create or update their public profile. */
public record WorkerProfileRequest(
        @NotBlank String categorySlug,
        @NotBlank String profession,
        @NotNull @Positive Integer price,
        @NotBlank String location,
        List<String> languages,
        List<String> skills,
        String about,
        Boolean available
) {}
