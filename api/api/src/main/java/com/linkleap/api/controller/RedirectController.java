package com.linkleap.api.controller;

import com.linkleap.api.entity.UrlMapping;
import com.linkleap.api.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.util.Optional;

// We use @Controller here instead of @RestController.
// Why? Because we are not returning a JSON body. We are performing a browser redirect.
@Controller
public class RedirectController {

    private final UrlShortenerService urlShortenerService;

    public RedirectController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * Redirects a short key to its original URL.
     * @param shortKey The short key to look up.
     * @return A ResponseEntity that either redirects the user or returns a 404 Not Found.
     */
    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey) {
        // Step 1: Call the service to find the UrlMapping.
        Optional<UrlMapping> urlMappingOptional = urlShortenerService.findByShortKey(shortKey);

        // Step 2: Check if the optional contains a value.
        if (urlMappingOptional.isPresent()) {
            // Step 2.a: Get the original URL from the entity.
            String originalUrl = urlMappingOptional.get().getOriginalUrl();

            // Step 2.b: Build a ResponseEntity to perform the redirect.
            return ResponseEntity
                    .status(HttpStatus.FOUND) // Sets the HTTP status to 302 Found
                    .location(URI.create(originalUrl)) // Sets the "Location" header to the original URL
                    .build(); // Builds the response with an empty body
        } else {
            // Step 3: If the optional is empty, the key was not found.
            // Return a 404 Not Found response.
            return ResponseEntity.notFound().build();
        }
    }
}