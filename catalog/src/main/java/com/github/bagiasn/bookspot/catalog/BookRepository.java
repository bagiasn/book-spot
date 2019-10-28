package com.github.bagiasn.bookspot.catalog;

import com.github.bagiasn.bookspot.common.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
