package com.example.urlshortener.url.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "active_short_urls")
@Getter
public class ActiveShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "short_url_id", nullable = false, foreignKey = @ForeignKey(name = "fk_active_short_urls_short_urls"))
    private ShortUrl shortUrl;

    @Setter
    @Size(min = 5, max = 65535)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String fullUrl;

    @Setter
    @Size(min = 5, max = 65535)
    @Column(columnDefinition = "TEXT", unique = true)
    private String shortUrlPathVariable;

}