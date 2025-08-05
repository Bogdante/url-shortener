package com.example.urlshortener.url.controllers;

import com.example.urlshortener.url.entities.ActiveShortUrl;
import com.example.urlshortener.url.repositories.ActiveShortUrlRepository;
import com.example.urlshortener.url.services.ActiveShortUrlService;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class ActiveShortUrlController {

    private final ActiveShortUrlService activeShortUrlService;

    public ActiveShortUrlController(ActiveShortUrlService activeShortUrlService) {
        this.activeShortUrlService = activeShortUrlService;
    }

    @GetMapping("/{shortUrlPathVariable}")
    public RedirectView redirectToFullUrl(
        @PathVariable
        @Size(min = 5, max = 65535)
        String shortUrlPathVariable
    ) {
        Optional<String> fullUrl = this.activeShortUrlService.findFullUrl(shortUrlPathVariable);
        return fullUrl.map(RedirectView::new).orElseGet(() -> new RedirectView("/"));
    }
}
