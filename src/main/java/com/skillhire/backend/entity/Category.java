package com.skillhire.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Mirrors the frontend's `Category` interface in src/lib/mock-data.ts. */
@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "slug"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String name;

    // Lucide icon name as a string (e.g. "Zap", "Wrench") so the frontend
    // can map it back to the matching lucide-react component.
    @Column(nullable = false)
    private String icon;

    // Hex color, e.g. "#f59e0b"
    @Column(nullable = false)
    private String color;
}
