package cz.solaracz.library_management_api.service;

import cz.solaracz.library_management_api.dto.BookRequest;
import cz.solaracz.library_management_api.exception.ResourceNotFoundException;
import cz.solaracz.library_management_api.model.Author;
import cz.solaracz.library_management_api.model.Book;
import cz.solaracz.library_management_api.model.Genre;
import cz.solaracz.library_management_api.repository.AuthorRepository;
import cz.solaracz.library_management_api.repository.BookRepository;
import cz.solaracz.library_management_api.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public List<Book> findBooks(Long authorId, Long genreId) {
        if (authorId == null && genreId == null) {
            return bookRepository.findAll();
        }
        return bookRepository.searchBooks(authorId, genreId);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(BookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.getAuthorId()));

        Genre genre = genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + request.getGenreId()));

        Book book = new Book(
                request.getTitle(),
                request.getIsbn(),
                request.getPublicationYear(),
                author,
                genre
        );

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.getAuthorId()));

        Genre genre = genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + request.getGenreId()));

        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setAuthor(author);
        book.setGenre(genre);

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}