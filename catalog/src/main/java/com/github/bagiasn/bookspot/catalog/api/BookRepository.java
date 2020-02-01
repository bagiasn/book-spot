package com.github.bagiasn.bookspot.catalog.api;

import com.github.bagiasn.bookspot.catalog.models.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

}
