package com.example.urlshortener.url.repositories;

import com.example.urlshortener.url.entities.ActiveShortUrl;
import com.example.urlshortener.url.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActiveShortUrlRepository extends JpaRepository<ActiveShortUrl, Long> {
    Optional<ActiveShortUrl> findByShortUrlPathVariable(String shortUrlPathVariable);
}
