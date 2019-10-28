package com.github.bagiasn.bookspot.catalog;

import com.github.bagiasn.bookspot.catalog.api.BookRepository;
import com.github.bagiasn.bookspot.common.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final BookRepository repository;

    @Autowired
    public DatabaseLoader(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setRating(2.3);
        book.setAuthorId((1));
        book.setCategoryId(1);
        book.setPublisherId(1);

        this.repository.save(book);
    }
}
