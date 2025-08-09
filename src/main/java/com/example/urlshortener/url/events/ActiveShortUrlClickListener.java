package com.example.urlshortener.url.events;

import com.example.urlshortener.url.entities.ActiveShortUrl;
import com.example.urlshortener.url.entities.ShortUrl;
import com.example.urlshortener.url.services.ActiveShortUrlService;
import com.example.urlshortener.url.services.ShortUrlService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ActiveShortUrlClickListener {

    private final ShortUrlService shortUrlService;
    private final ActiveShortUrlService activeShortUrlService;

    @Async
    @Transactional
    @EventListener
    public void handleActiveShortUrlClick(ActiveShortUrlClickEvent event) {
        ActiveShortUrl activeShortUrl = event.getActiveShortUrl();
        ShortUrl shortUrl = activeShortUrl.getShortUrl();
        shortUrlService.addClicksCount(shortUrl);

        if (!shortUrlService.isValid(shortUrl)) {
            shortUrlService.deactivate(shortUrl);
            activeShortUrlService.deleteActiveShortUrl(activeShortUrl);
        }
    }
}
