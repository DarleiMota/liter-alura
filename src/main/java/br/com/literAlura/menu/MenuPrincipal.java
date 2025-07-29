package br.com.literAlura.menu;

import br.com.literAlura.service.AuthorService;
import br.com.literAlura.service.BookService;

import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner scanner = new Scanner(System.in);
    private final BookService bookService;
    private final AuthorService authorService;

    public MenuPrincipal(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }
}
