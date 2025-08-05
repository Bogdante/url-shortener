package com.example.urlshortener.url.services;

import com.example.urlshortener.url.entities.ActiveShortUrl;
import com.example.urlshortener.url.entities.ShortUrl;
import com.example.urlshortener.url.repositories.ActiveShortUrlRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActiveShortUrlService {

    private final ActiveShortUrlRepository activeShortUrlRepository;

    public ActiveShortUrlService(ActiveShortUrlRepository activeShortUrlRepository) {
        this.activeShortUrlRepository = activeShortUrlRepository;
    }

    public void createForShortUrl(ShortUrl shortUrl) {
        ActiveShortUrl activeShortUrl = new ActiveShortUrl();
        activeShortUrl.setFullUrl(shortUrl.getFullUrl());
        activeShortUrl.setShortUrlPathVariable(shortUrl.getShortUrlPathVariable());

        activeShortUrlRepository.save(activeShortUrl);
    }

    public Optional<String> findFullUrl(String shortUrlPathVariable) {
        Optional<ActiveShortUrl> url = this.activeShortUrlRepository
                .findByShortUrlPathVariable(shortUrlPathVariable);
        return url.map(ActiveShortUrl::getFullUrl);
    }
}
