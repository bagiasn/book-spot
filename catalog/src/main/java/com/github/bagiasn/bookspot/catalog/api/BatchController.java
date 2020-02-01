package com.github.bagiasn.bookspot.catalog.api;

import com.github.bagiasn.bookspot.catalog.models.Author;
import com.github.bagiasn.bookspot.catalog.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/batch")
public class BatchController {

    private  AuthorRepository authorRepository;
    private  BookRepository bookRepository;

    @Autowired
    public BatchController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    public HttpStatus batchAuthorInsert(@RequestBody List<Author> authors) {

        authorRepository.saveAll(authors);

        return HttpStatus.OK;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public HttpStatus batchBookInsert(@RequestBody List<Book> books) {

        bookRepository.saveAll(books);

        return HttpStatus.OK;
    }
}
