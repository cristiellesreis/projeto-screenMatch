package br.com.alura.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    private final String ENDERECO_OMDB = "https://www.omdbapi.com";
    private final String apiKey;

    public ConsumoApi() {
        this.apiKey = null;
    }

    public ConsumoApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public String obterDados(String titulo, Integer temporada) {

        if (apiKey == null) {
            throw new IllegalStateException("API key não configurada.");
        }

        StringBuilder urlBuilder = new StringBuilder(ENDERECO_OMDB);
        urlBuilder.append("?apikey=").append(apiKey);

        if (titulo != null && !titulo.isEmpty()) {
            urlBuilder.append("&t=").append(titulo);
        }

        if (temporada != null) {
            urlBuilder.append("&season=").append(temporada);
        }

        return fazerRequisicao(urlBuilder.toString());
    }

    public String obterDados(String urlCompleta) {
        return fazerRequisicao(urlCompleta);
    }

    private String fazerRequisicao(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao fazer requisição HTTP para: " + url, e);
        }
    }
}
