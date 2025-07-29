package br.com.literAlura.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Scanner;

@Service
public class MenuService {
    private final BookService bookService;
    private final AuthorService authorService;
    private final Scanner scanner;
    private final String[] IDIOMAS_VALIDOS = {"pt", "es", "en", "fr", "de"};

    public MenuService(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            exibirOpcoes();

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números de 0 a 5");
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println("\n-------- LITERALURA --------");
        System.out.println("1 - Buscar livros por título");
        System.out.println("2 - Listar livros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Buscar autores vivos por ano");
        System.out.println("5 - Listar livros por idioma");
        System.out.println("0 - Sair");
        System.out.println("---------------------------------");
        System.out.print("Escolha uma opção: ");
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> buscarLivrosPorTitulo();
            case 2 -> listarLivrosRegistrados();
            case 3 -> listarAutoresRegistrados();
            case 4 -> listarAutoresVivosPorAno();
            case 5 -> listarLivrosPorIdioma();
            case 0 -> System.out.println("Saindo do sistema...");
            default -> System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private void buscarLivrosPorTitulo() {
        System.out.println("\n--- BUSCA POR TÍTULO ---");
        System.out.print("Digite o título para buscar: ");
        String titulo = scanner.nextLine();

        if (titulo.isBlank()) {
            System.out.println("Erro: Título não pode estar vazio");
            return;
        }

        bookService.importarLivros(titulo);
    }

    private void listarLivrosRegistrados() {
        System.out.println("\n--- LIVROS REGISTRADOS ---\n");
        bookService.listarLivrosFormatado();

    }

    private void listarAutoresRegistrados() {
        System.out.println("\n--- AUTORES REGISTRADOS ---\n");
        authorService.listarAutoresFormatado();
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("\n--- AUTORES VIVOS POR ANO ---\n");
        try {
            System.out.print("Digite o ano: ");
            int ano = Integer.parseInt(scanner.nextLine());
            authorService.listarAutoresVivosPorAno(ano);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um ano válido");
        }
    }
    private void listarLivrosPorIdioma() {
        System.out.println("\n--- LIVROS POR IDIOMA ---\n");
        System.out.println("Idiomas disponíveis: " + String.join(", ", IDIOMAS_VALIDOS));
        System.out.print("Digite o código do idioma: ");
        String idioma = scanner.nextLine().toLowerCase().trim();

        if (!Arrays.asList(IDIOMAS_VALIDOS).contains(idioma)) {
            System.out.println("Erro: Idioma inválido");
            return;
        }

        bookService.listarLivrosPorIdioma(idioma);
    }
}