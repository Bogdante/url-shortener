package com.example.urlshortener.url.requests;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateDefaultShortLinkRequest {
    @NotBlank
    @Size(min = 5, max = 1000)
    @JsonAlias("full_url")
    private String fullUrl;
}
