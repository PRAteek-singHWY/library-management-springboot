package com.library.service;

import com.library.model.Author;
import com.library.model.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private LibraryService libraryService;

    @Test
    public void testGetAllBooks() {
        Author author = new Author("Test Author", "US");
        Book book = new Book("Test Title", "Sci-Fi", "123", author);
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(Arrays.asList(book));

        List<Book> books = libraryService.getAllBooks();
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Title");
    }

    @Test
    public void testSaveBook_DuplicateIsbnThrowsException() {
        Book book = new Book("Test Title", "Sci-Fi", "123", new Author());
        when(bookRepository.save(any(Book.class))).thenThrow(DataIntegrityViolationException.class);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> libraryService.saveBook(book)
        );

        assertThat(thrown.getMessage()).contains("Book with this ISBN already exists.");
    }
}
