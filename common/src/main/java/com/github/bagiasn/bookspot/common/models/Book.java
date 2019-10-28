package com.github.bagiasn.bookspot.common.models;

import javax.persistence.*;
import java.sql.Date;

@Entity()
@Table(name = "books", schema = "catalog")
public class Book {

    private long id;
    private String title;
    private String frontpageUrl;
    private String description;
    private double rating;
    private String isbn;
    private Date publicationDate;
    private long pageCount;
    private String language;
    private long edition;
    private long authorId;
    private long categoryId;
    private long publisherId;

    public Book() {}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrontpageUrl() {
        return frontpageUrl;
    }

    public void setFrontpageUrl(String frontpageUrl) {
        this.frontpageUrl = frontpageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getEdition() {
        return edition;
    }

    public void setEdition(long edition) {
        this.edition = edition;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }
}