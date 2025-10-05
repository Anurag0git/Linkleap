package com.linkleap.api.controller;

import com.linkleap.api.dto.ShortenRequest;
import com.linkleap.api.entity.UrlMapping;
import com.linkleap.api.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    private final UrlShortenerService urlShortenerService;

    public WebController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    // This method shows the home page.
    @GetMapping("/")
    public String showHomePage(Model model) {
        // We now use "shortenRequest" to match the form object in the HTML.
        model.addAttribute("shortenRequest", new ShortenRequest());
        return "index";
    }

    // This method handles the form submission.
    @PostMapping("/shorten-web")
    public String createShortUrl(
            // RULE #1: We validate the ShortenRequest object, not a String.
            @Valid @ModelAttribute("shortenRequest") ShortenRequest shortenRequest,
            // RULE #2: BindingResult comes IMMEDIATELY after the object being validated.
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {

        // If validation has errors, show the form again.
        if (bindingResult.hasErrors()) {
            return "index"; // Thymeleaf will automatically find and show the errors.
        }

        // If no errors, proceed with the logic.
        UrlMapping generatedUrlMapping = urlShortenerService.createShortUrl(shortenRequest.getOriginalUrl());
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        if((scheme.equals("http") && serverPort==80) ||
        (scheme.equals("https") && serverPort==443)) {
            baseUrl = scheme+ "://" + serverName;
        }else{
            baseUrl = scheme + "://" + serverName +  ":" + serverPort;
        }


        model.addAttribute("shortUrl", generatedUrlMapping);
        model.addAttribute("baseUrl", baseUrl);

        return "index";
    }
}