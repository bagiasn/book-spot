package com.github.bagiasn.bookspot.common.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "categories", schema = "catalog")
public class Category {
    @Id
    private long id;
    private String name;

    public String getName() {
        return name;
    }
}