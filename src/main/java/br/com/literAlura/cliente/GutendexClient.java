package br.com.literAlura.cliente;

import br.com.literAlura.dto.BookDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GutendexClient {

    public List<BookDTO> buscarLivros(String termo) {
        String url = "https://gutendex.com/books/?search=" + termo.replace(" ", "+");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode results = root.get("results");

            return List.of(mapper.readValue(results.toString(), BookDTO[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao buscar dados da API", e);
        }
    }
}

