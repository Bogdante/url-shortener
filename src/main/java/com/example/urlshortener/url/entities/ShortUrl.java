package com.example.urlshortener.url.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls")
@Getter
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Size(min = 5, max = 65535)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String fullUrl;

    @Setter
    @Size(min = 5, max = 65535)
    @Column(columnDefinition = "TEXT")
    private String shortUrlPathVariable;

    @Setter
    @Column(nullable = false)
    private long clickCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    private boolean isActive;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.clickCount = 0;
    }

    public void incrementClicksCount() {
        this.clickCount++;
    }
}
