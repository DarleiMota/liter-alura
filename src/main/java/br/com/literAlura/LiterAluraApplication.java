package br.com.literAlura;

import br.com.literAlura.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LiterAluraApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Bean
    CommandLineRunner run(BookService bookService) {
        return args -> {
            bookService.importarLivros("Frankenstein");
        };
    }
}
