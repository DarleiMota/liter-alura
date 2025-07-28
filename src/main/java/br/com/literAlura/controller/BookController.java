package br.com.literAlura.controller;


import br.com.literAlura.model.Book;
import br.com.literAlura.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> listarTodos(){
        return bookService.listarTodos();
    }

    @GetMapping("/{id}")
    public Book buscarPorId(@PathVariable Long id) {
        return bookService.buscarPorId(id);
    }

    @PostMapping("/import")
    public void importarLivros(@RequestParam String termo) {
        bookService.importarLivros(termo);
    }

}
