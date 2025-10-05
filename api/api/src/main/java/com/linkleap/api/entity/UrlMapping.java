package com.linkleap.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity// tells spring this class represents a table
//specifies table name in the db
@Table(name= "url_mapping")
public class UrlMapping {

    @Id // marks this field as a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // lets the db handle generating the id
    private Long id;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;


    //unique short code for url, it must be unique
    @Column(name = "short_key", unique = true)
    private String shortKey;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    // jpa will automatically call this method right before the entity is saved to db
    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
    }


}
