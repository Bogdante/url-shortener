package com.example.urlshortener.url.services;

import com.example.urlshortener.url.entities.ActiveShortUrl;
import com.example.urlshortener.url.entities.ShortUrl;
import com.example.urlshortener.url.repositories.ActiveShortUrlRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ActiveShortUrlService {

    private final ActiveShortUrlRepository activeShortUrlRepository;
    private final RedisCacheService redisCacheService;

    public void createForShortUrl(@NotNull ShortUrl shortUrl) {
        ActiveShortUrl activeShortUrl = new ActiveShortUrl();
        activeShortUrl.setFullUrl(shortUrl.getFullUrl());
        activeShortUrl.setShortUrlPathVariable(shortUrl.getShortUrlPathVariable());
        activeShortUrl.setShortUrl(shortUrl);

        activeShortUrlRepository.save(activeShortUrl);
    }

    public void deleteActiveShortUrl(@NotNull ActiveShortUrl activeShortUrl) {
        activeShortUrlRepository.delete(activeShortUrl);
    }

    public Optional<String> findFullUrl(@NotNull String shortUrlPathVariable) {
        Optional<String> fullUrl = redisCacheService.get(shortUrlPathVariable);
        if (fullUrl.isPresent()) {
            return fullUrl;
        }

        Optional<ActiveShortUrl> url = activeShortUrlRepository
                .findByShortUrlPathVariable(shortUrlPathVariable);
        return url.map(ActiveShortUrl::getFullUrl);
    }
}
