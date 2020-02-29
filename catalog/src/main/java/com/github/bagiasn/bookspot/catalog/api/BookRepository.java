package com.github.bagiasn.bookspot.catalog.api;

import com.github.bagiasn.bookspot.catalog.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @RestResource(path = "/byRatingAsc", rel = "/byRatingAsc")
    Page<Book> findAllByOrderByRatingAsc(Pageable pageable);

    @RestResource(path = "/byRatingDesc", rel = "/byRatingDesc")
    Page<Book> findAllByOrderByRatingDesc(Pageable pageable);

    @RestResource(path = "/byYearDesc", rel = "/byYearDesc")
    Page<Book> findAllByOrderByPublicationYearDesc(Pageable pageable);

    @RestResource(path = "/byYearAsc", rel = "/byYearAsc")
    Page<Book> findAllByOrderByPublicationYearAsc(Pageable pageable);

    @RestResource(path = "/byCategory", rel = "/byCategory")
    Page<Book> findAllByCategory_Name(@Param("name") String categoryName, Pageable pageable);
}
