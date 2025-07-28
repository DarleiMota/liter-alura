package br.com.literAlura.service;

import br.com.literAlura.cliente.GutendexClient;
import br.com.literAlura.dto.AuthorDTO;
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

            AuthorDTO authorDTO = dto.getAuthors().get(0);
            String authorName = authorDTO.getName();
            Integer birthYear = authorDTO.getBirthYear();
            Integer deathYear = authorDTO.getDeathYear();

            //Evita Duplicar autores
            Author author = authorRepository
                    .findByNameIgnoreCase(authorName)
                    .orElseGet(()-> authorRepository.save(
                            new Author(authorName,birthYear,deathYear)
                    ));

            //Cria o livro com idioma
            String language = dto.getLanguages().isEmpty() ? "unknown" : dto.getLanguages().get(0);

            Book book = new Book(
                                dto.getTitle(),
                                dto.getDownloadCount(),
                                author,
                                language);

            bookRepository.save(book);

            System.out.println(
                    "Livro salvo: " + book.getTitle() +
                            " | Autor: " + author.getName() +
                            " | Nascimento: "+ birthYear +
                            " | Morte: " + deathYear+
                            " | Idioma: "+ language
            );
            System.out.println("DEBUG => AuthorDTO: " + authorName + ", nascimento: " + birthYear + ", morte: " + deathYear);
        }
    }

    public List<Book> listarTodos() {
        return bookRepository.findAll();
    }

    public Book buscarPorId(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro com ID " + id + " não encontrado"));
    }

}
