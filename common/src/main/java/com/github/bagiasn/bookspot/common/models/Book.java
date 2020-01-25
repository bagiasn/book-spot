package com.github.bagiasn.bookspot.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity()
@Table(name = "books", schema = "catalog")
public class Book implements Serializable {

    private long id;
    private String title;
    @Column(name = "cover_id")
    @JsonProperty(value = "cover_id")
    private long coverId;
    private String description;
    private double rating;
    @Type(type = "pg-uuid")
    private UUID isbn;
    @Column(name = "publication_date")
    @JsonProperty(value = "publication_date")
    private String publicationDate;
    private long pageCount;
    private String language;
    private long edition;
    @Column(name = "author_id")
    @JsonProperty(value = "author_id")
    private long authorId;
    @Column(name = "category_id")
    @JsonProperty(value = "category_id")
    private long categoryId;
    @Column(name = "publisher_id")
    @JsonProperty(value = "publisher_id")
    private long publisherId;
    @JsonInclude()
    @JsonProperty(value = "category_name")
    @Transient
    private String categoryName;

    public Book() {}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq_gen")
    @SequenceGenerator(name = "books_seq_gen", sequenceName = "catalog.books_id_seq", allocationSize = 1)
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

    public long getCoverId() {
        return coverId;
    }

    public void setCoverId(long coverId) {
        this.coverId = coverId;
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

    public UUID  getIsbn() {
        return isbn;
    }

    public void setIsbn(UUID  isbn) {
        this.isbn = isbn;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
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
    @Transient
    public String getCategoryName() {
        return categoryName;
    }
    @Transient
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}