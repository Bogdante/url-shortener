package com.example.urlshortener.url.services;

import com.example.urlshortener.url.entities.ShortUrl;
import com.example.urlshortener.url.repositories.ShortUrlRepository;
import io.seruco.encoding.base62.Base62;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ShortUrlService {

    private final static long CLICKS_LIMIT = 1000;
    private final static int VALID_DAYS = 1;

    private final ShortUrlRepository shortUrlRepository;
    private final ActiveShortUrlService activeShortUrlService;
    private final Base62 base62Encoder;

    public ShortUrlService(ShortUrlRepository shortUrlRepository,  ActiveShortUrlService activeShortUrlService) {
        this.shortUrlRepository = shortUrlRepository;
        this.activeShortUrlService =  activeShortUrlService;
        this.base62Encoder = Base62.createInstance();
    }

    @Transactional
    public ShortUrl createDefault(String fullUrl) {
        ShortUrl urlEntity = new ShortUrl();
        urlEntity.setFullUrl(fullUrl);
        urlEntity.setValidUntil(getValidUntil(LocalDate.now()));
        urlEntity =  this.shortUrlRepository.save(urlEntity);

        urlEntity.setShortUrlPathVariable(encodeIdBase62(urlEntity.getId()));
        this.activeShortUrlService.createForShortUrl(urlEntity);

        return this.shortUrlRepository.save(urlEntity);
    }

    public long getMaxClickCount() {
        return CLICKS_LIMIT;
    }

    private LocalDateTime getValidUntil(LocalDate date) {
        return date.plusDays(VALID_DAYS).atStartOfDay();
    }

    private String encodeIdBase62(Long id) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(id);
        byte[] idBytes = buffer.array();
        byte[] encodedBytes = this.base62Encoder.encode(idBytes);
        return new String(encodedBytes);
    }
}
