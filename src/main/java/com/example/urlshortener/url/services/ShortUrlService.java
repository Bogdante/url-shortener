package com.example.urlshortener.url.services;

import com.example.urlshortener.base62.Base62;
import com.example.urlshortener.common.AppConfig;
import com.example.urlshortener.url.entities.ShortUrl;
import com.example.urlshortener.url.repositories.ShortUrlRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ShortUrlService {

    private final static long CLICKS_LIMIT = 1000;
    private final static int VALID_DAYS = 1;

    private final AppConfig appConfig;
    private final ShortUrlRepository shortUrlRepository;
    private final ActiveShortUrlService activeShortUrlService;
    private final Base62 base62Encoder;

    @Transactional
    public ShortUrl createDefault(String fullUrl) {
        ShortUrl urlEntity = new ShortUrl();
        urlEntity.setFullUrl(fullUrl);
        urlEntity.setValidUntil(defineExpirationDate(LocalDate.now()));
        urlEntity = shortUrlRepository.save(urlEntity);

        urlEntity.setShortUrlPathVariable(base62Encoder.encode(urlEntity.getId()));
        activeShortUrlService.createForShortUrl(urlEntity);

        return shortUrlRepository.save(urlEntity);
    }


    public boolean isValid(ShortUrl shortUrl) {
        LocalDateTime now = LocalDateTime.now();
        return shortUrl.getClickCount() < getMaxClickCount()
                && shortUrl.getValidUntil().isAfter(now);
    }

    public void deactivate(ShortUrl shortUrl) {
        shortUrl.setActive(false);
        shortUrlRepository.save(shortUrl);
    }

    public void addClicksCount(ShortUrl shortUrl) {
        shortUrl.incrementClicksCount();
        shortUrlRepository.save(shortUrl);
    }

    public long getMaxClickCount() {
        return CLICKS_LIMIT;
    }

    public String generateShortUrlWithDomain(ShortUrl shortUrl) {
        return this.appConfig.getBaseUrl() + shortUrl.getShortUrlPathVariable();
    }

    private LocalDateTime defineExpirationDate(LocalDate date) {
        return date.plusDays(VALID_DAYS).atStartOfDay();
    }
}
