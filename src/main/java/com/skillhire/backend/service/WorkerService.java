package com.skillhire.backend.service;

import com.skillhire.backend.dto.worker.WorkerProfileRequest;
import com.skillhire.backend.dto.worker.WorkerResponse;
import com.skillhire.backend.entity.Category;
import com.skillhire.backend.entity.User;
import com.skillhire.backend.entity.WorkerProfile;
import com.skillhire.backend.enums.Role;
import com.skillhire.backend.exception.BadRequestException;
import com.skillhire.backend.exception.ResourceNotFoundException;
import com.skillhire.backend.repository.CategoryRepository;
import com.skillhire.backend.repository.UserRepository;
import com.skillhire.backend.repository.WorkerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerProfileRepository workerProfileRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    public List<WorkerResponse> search(String categorySlug, String location, String query) {
        return workerProfileRepository.search(
                        blankToNull(categorySlug), blankToNull(location), blankToNull(query))
                .stream()
                .map(mapper::toWorkerResponse)
                .toList();
    }

    public WorkerResponse getById(Long id) {
        WorkerProfile w = workerProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker not found: " + id));
        return mapper.toWorkerResponse(w);
    }

    @Transactional
    public WorkerResponse createOrUpdateProfile(Long userId, WorkerProfileRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        if (user.getRole() != Role.WORKER) {
            throw new BadRequestException("Only accounts registered with role WORKER can create a worker profile.");
        }
        Category category = categoryRepository.findBySlug(req.categorySlug())
                .orElseThrow(() -> new ResourceNotFoundException("Unknown category: " + req.categorySlug()));

        WorkerProfile profile = workerProfileRepository.findByUser_Id(userId)
                .orElseGet(() -> WorkerProfile.builder().user(user).build());

        profile.setCategory(category);
        profile.setProfession(req.profession());
        profile.setPrice(req.price());
        profile.setLocation(req.location());
        profile.setLanguages(req.languages() != null ? req.languages() : List.of());
        profile.setSkills(req.skills() != null ? req.skills() : List.of());
        profile.setAbout(req.about());
        if (req.available() != null) {
            profile.setAvailable(req.available());
        }

        return mapper.toWorkerResponse(workerProfileRepository.save(profile));
    }

    private String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}
