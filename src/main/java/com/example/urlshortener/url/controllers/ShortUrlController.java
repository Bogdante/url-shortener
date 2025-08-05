package com.example.urlshortener.url.controllers;

import com.example.urlshortener.url.entities.ShortUrl;
import com.example.urlshortener.url.requests.CreateDefaultShortLinkRequest;
import com.example.urlshortener.url.services.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/short-links")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> shortUrl(
        @Valid
        @RequestBody
        CreateDefaultShortLinkRequest request
    ) {
        String fullUrl = request.getFullUrl();
        ShortUrl url = shortUrlService.createDefault(fullUrl);

        Long maxClicksCount = this.shortUrlService.getMaxClickCount();
        String shortUrlWithDomain = this.shortUrlService.generateShortUrlWithDomain(url);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Map.of(
            "status", "created",
            "data", Map.of(
                    "id", url.getId(),
                    "valid_until", url.getValidUntil(),
                    "short_url", shortUrlWithDomain,
                    "max_clicks", maxClicksCount
                )
            )
        );
    }
}
