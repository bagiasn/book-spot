package com.github.bagiasn.bookspot.catalog.models;

import javax.persistence.*;

@Entity()
@Table(name = "publishers", schema = "catalog")
public class Publisher {

    private long id;
    private String name;

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
