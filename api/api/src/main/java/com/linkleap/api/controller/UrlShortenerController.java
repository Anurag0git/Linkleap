package com.linkleap.api.controller;
import com.linkleap.api.dto.ShortenRequest;
import com.linkleap.api.entity.UrlMapping;
import com.linkleap.api.service.UrlShortenerService;
import jakarta.persistence.PrePersist;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Optional;

@RestController // tells spring this class will handle API requests amd return JSON
@RequestMapping("api/v1/urls") // All endpoints in this class will start with this path
public class UrlShortenerController {
    //the controller now depends on service laer, not the repository
    private UrlShortenerService urlShortenerService;
    //we inject service using the constructor
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }
    // defines an endpoint that listenes for POST requests at /api/v1/urls

    @PostMapping
    public ResponseEntity<UrlMapping> shortenUrl(@Valid @RequestBody ShortenRequest shortenRequest) {
        //request body tells spring to convert incoming JSON into our shortenRequest object

//        System.out.println("URL to shorten: " + shortenRequest.getOriginalUrl());
        //responseEntity allows us to control HTTP response, including status code(eg here 200 ok)
//        return ResponseEntity.ok("URL received! will be processed soon.");
        UrlMapping urlMapping = urlShortenerService.createShortUrl(shortenRequest.getOriginalUrl());

        return ResponseEntity.ok(urlMapping);

    }



}
