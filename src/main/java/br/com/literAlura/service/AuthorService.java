package br.com.literAlura.service;

import br.com.literAlura.model.Author;
import br.com.literAlura.model.Book;
import br.com.literAlura.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Exibir autores formatado
    public void listarAutoresFormatado() {
        List<Author> autores = authorRepository.findAllWithBooks();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado.");
            return;
        }

        autores.forEach(autor -> {
            System.out.println("""
                    -------- Autor --------
                    Autor: %s
                    Ano de Nascimento: %s
                    Ano de Falecimento: %s
                    Livros:
                    %s
                    --------------------------
                    """.formatted(
                    autor.getName(),
                    autor.getBirthYear() == null ? "Desconhecido" : autor.getBirthYear(),
                    autor.getDeathYear() == null ? "Desconhecido" : autor.getDeathYear(),
                    autor.getBooks()
                            .stream()
                            .map(Book::getTitle)
                            .reduce("", (acc, title) -> acc + " - " + title + "\n")
            ));
        });
        System.out.println("Total: " + autores.size() + " autores");
    }

    //Exibir autores vivos pelo ano fornecido.
    public void listarAutoresVivosPorAno(int ano) {
        List<Author> autores = authorRepository.findAllWithBooks()
                .stream()
                .filter(a -> a.getBirthYear() != null && a.getBirthYear() <= ano &&
                        (a.getDeathYear() == null || a.getDeathYear() > ano))
                .toList();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado no ano " + ano + ".");
            return;
        }

        autores.forEach(autor -> {
            System.out.println("""
                    \n-------- Autor --------
                    Autor: %s
                    Ano de Nascimento: %s
                    Ano de Falecimento: %s
                    Livro:
                    %s
                    --------------------------
                    """.formatted(
                    autor.getName(),
                    autor.getBirthYear(),
                    autor.getDeathYear() == null ? "Ainda vivo ou desconhecido" : autor.getDeathYear(),
                    autor.getBooks()
                            .stream()
                            .map(Book::getTitle)
                            .reduce("", (acc, title) -> acc + "- " + title + "\n")
            ));
        });
        System.out.println("\nTotal: " + autores.size() + " autores");
    }
}

