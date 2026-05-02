package com.library.repository;

import com.library.model.Author;
import com.library.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testFindAllBooksWithAuthors() {
        Author author = new Author("Test Author", "Test Nationality");
        author = authorRepository.save(author);

        Book book = new Book("Test Book", "Fiction", "1234567890", author);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAllBooksWithAuthors();
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor().getName()).isEqualTo("Test Author");
    }
}
