package com.github.bagiasn.bookspot.catalog.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity()
@Table(name = "books")
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
    @Column(name = "publication_year")
    @JsonProperty(value = "publication_year")
    private int publicationYear;
    private long pageCount;
    private String language;
    private long edition;

    private Author author;
    private Category category;
    private Publisher publisher;

    public Book() {}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq_gen")
    @SequenceGenerator(name = "books_seq_gen", sequenceName = "books_id_seq", allocationSize = 1)
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

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
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

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}