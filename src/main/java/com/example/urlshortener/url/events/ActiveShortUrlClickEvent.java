package com.example.urlshortener.url.events;

import com.example.urlshortener.url.entities.ActiveShortUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActiveShortUrlClickEvent {
    private final ActiveShortUrl  activeShortUrl;
}
