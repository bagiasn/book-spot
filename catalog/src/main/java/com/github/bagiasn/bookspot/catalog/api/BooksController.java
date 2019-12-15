package com.github.bagiasn.bookspot.catalog.api;

import com.github.bagiasn.bookspot.common.models.Book;
import com.github.bagiasn.bookspot.common.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/catalog")
public class BooksController {
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public BooksController(
                BookRepository bookRepository,
                CategoryRepository catalogRepository) {

        this.bookRepository = bookRepository;
        this.categoryRepository = catalogRepository;
    }

    @Cacheable("books")
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public Iterable<Book> get(Pageable pageable) {

        Iterable<Book> books = bookRepository.findAll(pageable);

        for (Book book : books) {
            Optional<Category> category = categoryRepository.findById(book.getCategoryId());
            category.ifPresent(value -> book.setCategoryName(value.getName()));
        }

        return books;
    }
}
