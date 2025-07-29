package br.com.literAlura;

import br.com.literAlura.service.MenuService;
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
    public CommandLineRunner iniciarMenu(MenuService menuService) {
        return argumentosDaLinhaDeComando -> menuService.exibirMenu();
    }
}
