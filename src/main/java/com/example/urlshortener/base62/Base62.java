package com.example.urlshortener.base62;

import org.springframework.stereotype.Component;

@Component
public class Base62 {
    private final String alphabet;

    public Base62() {
        this.alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    }

    public String encode(Long value) {
        long integerPart = value;
        long fractionalPart;
        StringBuilder encodedString = new StringBuilder();

        while (integerPart > 0) {
            fractionalPart = integerPart % 62;
            encodedString.append(this.alphabet.charAt((int) fractionalPart));
            integerPart /= 62;
        }

        return encodedString.reverse().toString();
    }
}
