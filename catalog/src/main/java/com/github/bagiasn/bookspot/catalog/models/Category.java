package com.github.bagiasn.bookspot.catalog.models;

import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "categories")
public class Category {
    @Id
    private long id;
    private String name;
    @OneToMany(targetEntity = Book.class, mappedBy = "category", fetch = FetchType.EAGER)
    private List<Book> books;

    public Category() {
    }

    public String getName() {
        return name;
    }
}
