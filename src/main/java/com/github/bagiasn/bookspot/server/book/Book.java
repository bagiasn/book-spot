package com.github.bagiasn.bookspot.server.book;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "books")
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
