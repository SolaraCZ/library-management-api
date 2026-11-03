package cz.solaracz.library_management_api.config;

import cz.solaracz.library_management_api.model.Author;
import cz.solaracz.library_management_api.model.Book;
import cz.solaracz.library_management_api.model.Genre;
import cz.solaracz.library_management_api.repository.AuthorRepository;
import cz.solaracz.library_management_api.repository.BookRepository;
import cz.solaracz.library_management_api.repository.GenreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AuthorRepository authorRepository,
                                   GenreRepository genreRepository,
                                   BookRepository bookRepository) {
        return args -> {
            Author tolkien = authorRepository.save(new Author("J.R.R. Tolkien", "English writer and philologist"));
            Author orwell = authorRepository.save(new Author("George Orwell", "English novelist and essayist"));

            Genre fantasy = genreRepository.save(new Genre("Fantasy"));
            Genre sciFi = genreRepository.save(new Genre("Dystopian"));

            bookRepository.save(new Book("The Hobbit", "978-0261102217", 1937, tolkien, fantasy));
            bookRepository.save(new Book("The Fellowship of the Ring", "978-0261102354", 1954, tolkien, fantasy));
            bookRepository.save(new Book("1984", "978-0451524935", 1949, orwell, sciFi));
            bookRepository.save(new Book("Animal Farm", "978-0451526342", 1945, orwell, sciFi));
        };
    }
}