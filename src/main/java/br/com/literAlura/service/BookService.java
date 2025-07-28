package br.com.literAlura.service;

import br.com.literAlura.cliente.GutendexClient;
import br.com.literAlura.dto.BookDTO;
import br.com.literAlura.model.Author;
import br.com.literAlura.model.Book;
import br.com.literAlura.repository.AuthorRepository;
import br.com.literAlura.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GutendexClient client = new GutendexClient();

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void importarLivros(String termo) {
        List<BookDTO> bookDTOs = client.buscarLivros(termo);

        for (BookDTO dto : bookDTOs) {
            if (dto.getAuthors().isEmpty()) continue;

            String authorName = dto.getAuthors()
                    .get(0)
                    .getName();

            // Aqui evitamos duplicar autores nos livros
            Author author = authorRepository
                    .findByNameIgnoreCase(authorName)
                    .orElseGet(() -> authorRepository.save(new Author(authorName)));

            Book book = new Book(
                    dto.getTitle(),
                    dto.getDownloadCount(),
                    author);

            bookRepository.save(book);

            System.out.println(
                    "Livro salvo: " + book.getTitle() +
                            "| Autor: " + author.getName());
        }
    }

}
