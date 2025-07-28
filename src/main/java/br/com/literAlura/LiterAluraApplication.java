package br.com.literAlura;

import br.com.literAlura.cliente.GutendexClient;
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
    CommandLineRunner testApi() {
        return args -> {
            var client = new GutendexClient();
            var livros = client.buscarLivros("Frankenstein");

            livros.forEach(System.out::println);
        };
    }
}
