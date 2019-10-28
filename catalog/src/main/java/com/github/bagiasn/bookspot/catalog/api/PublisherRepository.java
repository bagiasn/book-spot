package com.github.bagiasn.bookspot.catalog.api;

import com.github.bagiasn.bookspot.common.models.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
