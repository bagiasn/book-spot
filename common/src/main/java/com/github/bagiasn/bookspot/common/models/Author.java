package com.github.bagiasn.bookspot.common.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "authors", schema = "catalog")
public class Author {

    private long id;
    private String name;
    @Column(name = "source_id")
    @JsonProperty(value = "source_id")
    private String sourceId;
    @Column(name = "birth_date")
    @JsonProperty(value = "birth_date")
    private String birthDate;
    @Column(name = "death_date")
    @JsonProperty(value = "death_date")
    private String deathDate;
    private String location;

    public Author() {}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authors_seq_gen")
    @SequenceGenerator(name = "authors_seq_gen", sequenceName = "catalog.authors_id_seq", initialValue = 1, allocationSize = 1)
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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

