package br.com.literAlura.service;

import br.com.literAlura.cliente.GutendexClient;
import br.com.literAlura.dto.AuthorDTO;
import br.com.literAlura.dto.BookDTO;
import br.com.literAlura.model.Author;
import br.com.literAlura.model.Book;
import br.com.literAlura.repository.AuthorRepository;
import br.com.literAlura.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GutendexClient client;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       GutendexClient client) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.client = client;
    }

    @Transactional
    public void importarLivros(String termo) {
        if (termo == null || termo.isBlank()) {
            System.out.println("Erro: Termo de busca vazio");
            return;
        }

        System.out.println("\nBuscando livros para: '" + termo + "'...\n");

        try {
            List<BookDTO> bookDTOs = client.buscarLivros(termo);

            if (bookDTOs.isEmpty()) {
                System.out.println("Nenhum livro encontrado para: '" + termo + "'");
                return;
            }

            for (BookDTO dto : bookDTOs) {
                try {
                    if (dto.getAuthors().isEmpty() || dto.getAuthors().get(0).getName() == null) {
                        continue;
                    }

                    // Adiciona autor
                    AuthorDTO authorDTO = dto.getAuthors().get(0);
                    Author author = authorRepository.findByNameIgnoreCase(authorDTO.getName())
                            .orElseGet(() -> authorRepository.save(
                                    new Author(
                                            authorDTO.getName(),
                                            authorDTO.getBirthYear(),
                                            authorDTO.getDeathYear()
                                    )
                            ));

                    // Título ajustado (DB e exibição)
                    String tituloOriginal = dto.getTitle().length() > 500 ?
                            dto.getTitle().substring(0, 500) : dto.getTitle();

                    String tituloExibicao = dto.getTitle().length() > 100 ?
                            dto.getTitle().substring(0, 97) + "..." : dto.getTitle();

                    // Verifica resultados duplicados
                    if (bookRepository.existsByTitleIgnoreCaseAndAuthor(tituloOriginal, author)) {
                        System.out.println("O Banco já tem o livro: " + tituloOriginal + "\n");
                        break;
                    }

                    // Cria livro e verifica livro
                    Book book = new Book(
                            tituloOriginal,
                            dto.getDownloadCount() != null ? dto.getDownloadCount() : 0,
                            author,
                            dto.getLanguages().isEmpty() ? "unknown" : dto.getLanguages().get(0)
                    );

                    bookRepository.save(book);

                    System.out.println("""
                    --------- LIVRO IMPORTADO ----------
                    Título: %s
                    Autor: %s (%d - %s)
                    Idioma: %s
                    Downloads: %,d
                    -------------------------------------
                    """.formatted(
                            tituloExibicao,
                            author.getName(),
                            author.getBirthYear() != null ? author.getBirthYear() : 0,
                            author.getDeathYear() != null ? author.getDeathYear() : "Presente",
                            book.getLanguage(),
                            book.getDownloadCount()
                    ));

                } catch (Exception e) {
                    // Não mostra erros individuais
                }
            }
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        // Chama para exibir consultas direto do banco
        exibirLivrosPorTituloSalvos(termo);
    }

    // Busca todos os livros
    public List<Book> listarTodos() {
        return bookRepository.findAll();
    }

    // Busca por ID
    public Book buscarPorId(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro com ID " + id + " não encontrado"));
    }

    // Exibir livros Formatados
    public void listarLivrosFormatado() {
        List<Book> livros = bookRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro cadastrado no banco de dados");
            return;
        }

        System.out.println("\nLISTA COMPLETA DE LIVROS:\n");
        livros.forEach(livro -> {
            System.out.println("""
                    --------- Livro ----------
                    Título: %s
                    Autor: %s (%d - %s)
                    Idioma: %s
                    Downloads: %,d
                    ---------------------------
                    """.formatted(
                    livro.getTitle().length() > 100 ? livro.getTitle().substring(0,97) + "..." : livro.getTitle(),
                    livro.getAuthor().getName(),
                    livro.getAuthor().getBirthYear() != null ? livro.getAuthor().getBirthYear() : 0,
                    livro.getAuthor().getDeathYear() != null ? livro.getAuthor().getDeathYear() : "Presente",
                    livro.getLanguage(),
                    livro.getDownloadCount()
            ));
        });
        System.out.println("\n Total: " + livros.size() + " livros");

    }

    // Livros por idioma
    public void listarLivrosPorIdioma(String idioma) {
        List<Book> livros = bookRepository.findAll()
                .stream()
                .filter(l -> l.getLanguage().equalsIgnoreCase(idioma))
                .toList();

        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro encontrado no idioma: " + idioma);
            return;
        }

        System.out.println("\nLIVROS NO IDIOMA '" + idioma.toUpperCase() + "':\n");
        livros.forEach(livro -> {
            System.out.println("""
                    --------- Livro ----------
                    Título: %s
                    Autor: %s
                    Downloads: %,d
                    ---------------------------
                    """.formatted(
                    livro.getTitle(),
                    livro.getAuthor().getName(),
                    livro.getDownloadCount()
            ));
        });
        System.out.println("\nTotal: " + livros.size() + " livros");
    }

    // Exibir os livros salvos pela pesquisa
    public void exibirLivrosPorTituloSalvos(String termo) {
        List<Book> livros = bookRepository.findByTitleContainingIgnoreCase(termo);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco para o termo: '" + termo + "'");
            return;
        }

        System.out.println("\n📚 LIVROS JÁ CADASTRADOS COM O TERMO '" + termo + "':");
        livros.forEach(livro -> {
            System.out.println("""
            -------------------------
            Título: %s
            Autor: %s (%d - %s)
            Idioma: %s
            Downloads: %,d
            -------------------------
            """.formatted(
                    livro.getTitle(),
                    livro.getAuthor().getName(),
                    livro.getAuthor().getBirthYear() != null ? livro.getAuthor().getBirthYear() : 0,
                    livro.getAuthor().getDeathYear() != null ? livro.getAuthor().getDeathYear() : "Presente",
                    livro.getLanguage(),
                    livro.getDownloadCount()
            ));
        });
    }
}
