package com.skillhire.backend.controller;

import com.skillhire.backend.dto.worker.WorkerProfileRequest;
import com.skillhire.backend.dto.worker.WorkerResponse;
import com.skillhire.backend.service.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    // GET /api/workers?category=plumber&location=Kathmandu&query=wiring
    @GetMapping
    public List<WorkerResponse> search(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String query) {
        return workerService.search(category, location, query);
    }

    @GetMapping("/{id}")
    public WorkerResponse getById(@PathVariable Long id) {
        return workerService.getById(id);
    }

    // A logged-in WORKER user creates/updates their public profile.
    // {userId} is the account id returned from /api/auth/login or /register.
    @PutMapping("/profile/{userId}")
    public WorkerResponse upsertProfile(@PathVariable Long userId, @Valid @RequestBody WorkerProfileRequest req) {
        return workerService.createOrUpdateProfile(userId, req);
    }
}
