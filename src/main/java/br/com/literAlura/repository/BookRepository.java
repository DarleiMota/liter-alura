package br.com.literAlura.repository;

import br.com.literAlura.model.Author;
import br.com.literAlura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitleIgnoreCaseAndAuthor(String title, Author author);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Book> findByTitleContainingIgnoreCase(@Param("titulo") String titulo);
}