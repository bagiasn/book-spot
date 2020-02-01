package com.github.bagiasn.bookspot.catalog.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "categories")
public class Category {
    @Id
    private long id;
    private String name;

    public String getName() {
        return name;
    }
}
