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
    private final RedisCacheService redisCacheService;

    public ActiveShortUrlService(
            ActiveShortUrlRepository activeShortUrlRepository,
            RedisCacheService redisCacheService
    ) {
        this.activeShortUrlRepository = activeShortUrlRepository;
        this.redisCacheService = redisCacheService;
    }

    public void createForShortUrl(ShortUrl shortUrl) {
        ActiveShortUrl activeShortUrl = new ActiveShortUrl();
        activeShortUrl.setFullUrl(shortUrl.getFullUrl());
        activeShortUrl.setShortUrlPathVariable(shortUrl.getShortUrlPathVariable());
        activeShortUrl.setShortUrl(shortUrl);

        activeShortUrlRepository.save(activeShortUrl);
    }

    public Optional<String> findFullUrl(String shortUrlPathVariable) {
        Optional<String> fullUrl = redisCacheService.get(shortUrlPathVariable);
        if (fullUrl.isPresent()) {
            return fullUrl;
        }

        Optional<ActiveShortUrl> url = activeShortUrlRepository
                .findByShortUrlPathVariable(shortUrlPathVariable);
        return url.map(ActiveShortUrl::getFullUrl);
    }
}
