package com.github.bagiasn.bookspot.server.unit;

import com.github.bagiasn.bookspot.server.DatabaseLoader;
import com.github.bagiasn.bookspot.server.book.Book;
import com.github.bagiasn.bookspot.server.book.BookRepository;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DatabaseLoaderTest {

    @Test
    public void databaseLoaderCallsSave() {

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        when(bookRepository.save(any(Book.class))).then(returnsFirstArg());

        DatabaseLoader loader = new DatabaseLoader(bookRepository);
        loader.run();

        verify(bookRepository, times(1)).save(any(Book.class));
    }
}
