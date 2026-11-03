package cz.solaracz.library_management_api.repository;

import cz.solaracz.library_management_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE " +
            "(:authorId IS NULL OR b.author.id = :authorId) AND " +
            "(:genreId IS NULL OR b.genre.id = :genreId)")
    List<Book> searchBooks(@Param("authorId") Long authorId, @Param("genreId") Long genreId);

    List<Book> findByTitleContainingIgnoreCase(String title);
}