package com.github.bagiasn.bookspot.server;

import com.github.bagiasn.bookspot.server.book.Book;
import com.github.bagiasn.bookspot.server.book.BookRepository;
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
        this.repository.save(new Book("Crime and Punishment", "Someone", "http://s.com", 34));
    }
}