package com.github.bagiasn.bookspot.catalog.api;

import com.github.bagiasn.bookspot.common.models.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
