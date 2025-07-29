package br.com.literAlura.repository;

import br.com.literAlura.model.Author;
import br.com.literAlura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitleIgnoreCaseAndAuthor(String title, Author author);
}