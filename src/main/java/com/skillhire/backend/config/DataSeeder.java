package com.skillhire.backend.config;

import com.skillhire.backend.entity.Category;
import com.skillhire.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Populates the categories table on startup so it matches src/lib/mock-data.ts
 * exactly (icon names are Lucide component names as strings). Runs once —
 * skips any slug that already exists, so it's safe on every restart.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        List<Category> seed = List.of(
                cat("electrician", "Electrician", "Zap", "#f59e0b"),
                cat("plumber", "Plumber", "Wrench", "#0ea5e9"),
                cat("carpenter", "Carpenter", "Hammer", "#a16207"),
                cat("painter", "Painter", "Paintbrush", "#ec4899"),
                cat("mechanic", "Mechanic", "Car", "#334155"),
                cat("ac-technician", "AC Technician", "Snowflake", "#38bdf8"),
                cat("cleaner", "Cleaner", "Sparkles", "#22c55e"),
                cat("home-maid", "Home Maid", "Home", "#8b5cf6"),
                cat("cook", "Cook", "ChefHat", "#ef4444"),
                cat("tutor", "Tutor", "GraduationCap", "#2563eb"),
                cat("photographer", "Photographer", "Camera", "#0f172a"),
                cat("videographer", "Videographer", "Video", "#7c3aed"),
                cat("gardener", "Gardener", "Trees", "#16a34a"),
                cat("driver", "Driver", "CarFront", "#0891b2"),
                cat("beautician", "Beautician", "Scissors", "#f43f5e"),
                cat("tailor", "Tailor", "Shirt", "#db2777"),
                cat("welder", "Welder", "Flame", "#ea580c"),
                cat("mason", "Mason", "Building2", "#78350f"),
                cat("computer-repair", "Computer Repair", "Laptop", "#475569"),
                cat("mobile-repair", "Mobile Repair", "Smartphone", "#0d9488"),
                cat("others", "Others", "MoreHorizontal", "#6b7280")
        );

        for (Category c : seed) {
            if (!categoryRepository.existsBySlug(c.getSlug())) {
                categoryRepository.save(c);
            }
        }
    }

    private Category cat(String slug, String name, String icon, String color) {
        return Category.builder().slug(slug).name(name).icon(icon).color(color).build();
    }
}
