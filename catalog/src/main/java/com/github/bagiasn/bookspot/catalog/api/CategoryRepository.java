package com.github.bagiasn.bookspot.catalog.api;

import com.github.bagiasn.bookspot.common.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
