package cz.solaracz.library_management_api.service;

import cz.solaracz.library_management_api.dto.BookRequest;
import cz.solaracz.library_management_api.exception.ResourceNotFoundException;
import cz.solaracz.library_management_api.model.Author;
import cz.solaracz.library_management_api.model.Book;
import cz.solaracz.library_management_api.model.Genre;
import cz.solaracz.library_management_api.repository.AuthorRepository;
import cz.solaracz.library_management_api.repository.BookRepository;
import cz.solaracz.library_management_api.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Genre genre;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("Arthur Conan Doyle", "Scottish physician and writer");
        author.setId(1L);

        genre = new Genre("Detective");
        genre.setId(1L);

        book = new Book("A Study in Scarlet", "978-0141023434", 1887, author, genre);
        book.setId(1L);
    }

    @Test
    void shouldReturnAllBooksWhenNoFiltersProvided() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.findBooks(null, null);

        assertEquals(1, result.size());
        assertEquals("A Study in Scarlet", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void shouldFilterBooksByAuthorAndGenre() {
        when(bookRepository.searchBooks(1L, 1L)).thenReturn(List.of(book));

        List<Book> result = bookService.findBooks(1L, 1L);

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).searchBooks(1L, 1L);
    }

    @Test
    void shouldCreateBookSuccessfully() {
        BookRequest request = new BookRequest();
        request.setTitle("The Sign of the Four");
        request.setIsbn("978-0141023441");
        request.setPublicationYear(1890);
        request.setAuthorId(1L);
        request.setGenreId(1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book created = bookService.createBook(request);

        assertNotNull(created);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldThrowExceptionWhenAuthorNotFoundOnCreate() {
        BookRequest request = new BookRequest();
        request.setAuthorId(99L);
        request.setGenreId(1L);

        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.createBook(request));
        verify(bookRepository, never()).save(any(Book.class));
    }
}