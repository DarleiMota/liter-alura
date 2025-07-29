package br.com.literAlura.cliente;

import br.com.literAlura.dto.BookDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books/?search=";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public GutendexClient() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public List<BookDTO> buscarLivros(String termo) {
        try {
            String url = BASE_URL + URLEncoder.encode(termo, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(50))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            JsonNode root = mapper.readTree(responseBody);
            JsonNode results = root.path("results");

            if (results.isMissingNode() || !results.isArray() || results.size() == 0) {
                System.out.println("Nenhum livro encontrado para: " + termo);
                return Collections.emptyList();
            }

            return mapper.readValue(results.traverse(), new TypeReference<List<BookDTO>>() {});

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro temporário. Por favor, tente novamente mais tarde.");
            return Collections.emptyList();
        }
    }
}