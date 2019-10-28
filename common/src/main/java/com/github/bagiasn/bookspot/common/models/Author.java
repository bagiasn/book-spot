package com.github.bagiasn.bookspot.common.models;

import javax.persistence.*;

@Entity
@Table(name = "authors", schema = "catalog")
public class Author {

    private long id;
    private String name;

    public Author() {}

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
