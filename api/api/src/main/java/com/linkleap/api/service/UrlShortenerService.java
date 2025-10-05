package com.linkleap.api.service;

import com.linkleap.api.entity.UrlMapping;
import com.linkleap.api.repository.UrlMappingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UrlShortenerService {
    private final UrlMappingRepository urlMappingRepository;
    private static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLKMNOPQRSTUVWXYZ0123456789";
    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }


    @Transactional // ensures two 'save' operations happen together or not at all
    public UrlMapping createShortUrl(String originalUrl){
        // step 1: create a new entity and set original url
    UrlMapping newUrlMapping = new UrlMapping();
    newUrlMapping.setOriginalUrl(originalUrl);

    // step 2 and 3: save it to db, the 'save' method returns saved entity which now has db-generated id
    UrlMapping savedEntity = urlMappingRepository.save(newUrlMapping);
    long databaseId = savedEntity.getId();

    // step 4: encode new db id into base62 string
        String shortKey = encodeToBase62(databaseId);

        savedEntity.setShortKey(shortKey);

        return urlMappingRepository.save(savedEntity);

    }

    private String encodeToBase62(long n) {
        if (n==0) return String.valueOf(BASE62_CHARS.charAt(0));
        StringBuilder sb=new StringBuilder();

        while (n > 0) {

            sb.append(BASE62_CHARS.charAt((int)(n % 62)));
            n/=62;
        }
        return sb.toString();
    }


    public Optional<UrlMapping> findByShortKey(String shortKey) {
        return urlMappingRepository.findByShortKey(shortKey);
    }
}