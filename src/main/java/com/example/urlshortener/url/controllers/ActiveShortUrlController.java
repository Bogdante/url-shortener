package com.example.urlshortener.url.controllers;

import com.example.urlshortener.url.entities.ActiveShortUrl;
import com.example.urlshortener.url.repositories.ActiveShortUrlRepository;
import com.example.urlshortener.url.services.ActiveShortUrlService;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class ActiveShortUrlController {

    private final ActiveShortUrlService activeShortUrlService;

    @GetMapping("/{shortUrlPathVariable}")
    public RedirectView redirectToFullUrl(
        @PathVariable
        @Size(min = 1, max = 255)
        String shortUrlPathVariable
    ) {
        Optional<String> fullUrl = this.activeShortUrlService.findFullUrl(shortUrlPathVariable);
        if (fullUrl.isPresent()) {
            return new RedirectView(fullUrl.get());
        }

        return new  RedirectView("/");
    }
}
