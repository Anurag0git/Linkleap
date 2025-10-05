package com.linkleap.api.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;


@Data // this is lombok annotation that creates getters, setters, constructor and toString() automatically
public class ShortenRequest {
    // this will hold URL sent by user in json request body
    // ex: {"originalUrl" : "https://www.google.com"}
    @NotEmpty(message = "URL cannot be empty")
    @URL(message = "Please provide a valid URL")
    private String originalUrl;

}
