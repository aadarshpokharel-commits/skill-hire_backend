package com.skillhire.backend.service;

import com.skillhire.backend.dto.auth.AuthResponse;
import com.skillhire.backend.dto.booking.BookingResponse;
import com.skillhire.backend.dto.category.CategoryResponse;
import com.skillhire.backend.dto.message.MessageResponse;
import com.skillhire.backend.dto.worker.WorkerResponse;
import com.skillhire.backend.entity.Booking;
import com.skillhire.backend.entity.Category;
import com.skillhire.backend.entity.Message;
import com.skillhire.backend.entity.User;
import com.skillhire.backend.entity.WorkerProfile;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class Mapper {

    private static final String[] AVATAR_COLORS = {
            "#f59e0b", "#0ea5e9", "#a16207", "#ec4899", "#334155",
            "#22c55e", "#8b5cf6", "#ef4444", "#2563eb", "#7c3aed"
    };

    public AuthResponse toAuthResponse(User u) {
        return new AuthResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }

    public CategoryResponse toCategoryResponse(Category c, long workerCount) {
        return new CategoryResponse(c.getId(), c.getSlug(), c.getName(), c.getIcon(), c.getColor(), workerCount);
    }

    public WorkerResponse toWorkerResponse(WorkerProfile w) {
        String name = w.getUser().getName();
        return new WorkerResponse(
                w.getId(),
                name,
                w.getProfession(),
                w.getCategory().getSlug(),
                w.getRating(),
                w.getReviewsCount(),
                w.getExperience(),
                w.getPrice(),
                w.getLocation(),
                w.getLanguages(),
                w.getSkills(),
                w.getAbout(),
                w.getAvailable(),
                w.getCompletedJobs(),
                w.getVerified(),
                initialsOf(name),
                colorFor(w.getId())
        );
    }

    public BookingResponse toBookingResponse(Booking b) {
        return new BookingResponse(
                b.getId(),
                b.getWorker().getId(),
                b.getWorker().getUser().getName(),
                b.getCustomer().getName(),
                b.getCategory().getSlug(),
                b.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                b.getTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                b.getAddress(),
                b.getDescription(),
                b.getStatus().name(),
                b.getPrice()
        );
    }

    public MessageResponse toMessageResponse(Message m) {
        return new MessageResponse(
                m.getId(),
                m.getSender().getId(),
                m.getReceiver().getId(),
                m.getContent(),
                m.getSentAt().toString(),
                m.getRead()
        );
    }

    private String initialsOf(String name) {
        String[] parts = name.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(2, parts.length); i++) {
            if (!parts[i].isEmpty()) sb.append(Character.toUpperCase(parts[i].charAt(0)));
        }
        return sb.length() > 0 ? sb.toString() : name.substring(0, Math.min(2, name.length())).toUpperCase(Locale.ROOT);
    }

    private String colorFor(Long id) {
        return AVATAR_COLORS[(int) (id % AVATAR_COLORS.length)];
    }
}
