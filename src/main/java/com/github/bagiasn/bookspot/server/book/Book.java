package com.github.bagiasn.bookspot.server.book;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Entity(name = "books")
@JsonAutoDetect(creatorVisibility = ANY, fieldVisibility = ANY)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String author;

    private String iconUrl;

    private long categoryId;

    private Book() {}

    public Book(String title, String author, String iconUrl, long categoryId) {
        this.title = title;
        this.author = author;
        this.iconUrl = iconUrl;
        this.categoryId = categoryId;
    }
}
