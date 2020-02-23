package com.github.bagiasn.bookspot.catalog.models;

import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "publishers")
public class Publisher {

    private long id;
    private String name;
    @OneToMany(targetEntity = Book.class, mappedBy = "publisher", fetch = FetchType.EAGER)
    private List<Book> books;

    public Publisher() {}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
