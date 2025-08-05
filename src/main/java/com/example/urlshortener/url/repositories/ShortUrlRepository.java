package com.example.urlshortener.url.repositories;

import com.example.urlshortener.url.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

}
