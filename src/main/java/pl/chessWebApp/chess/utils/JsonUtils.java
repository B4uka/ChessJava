package pl.chessWebApp.chess.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public final class JsonUtils {

    public String JSONBody (URI uri) throws ExecutionException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();
    }

    public Map<String, String> JSONBodyAsMap (URI uri) throws ExecutionException, InterruptedException {
        UncheckedObjectMapper objectMapper = new UncheckedObjectMapper();
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(objectMapper::readValue)
                .get();
    }

    class UncheckedObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
        Map<String, String> readValue (String content) {
            try {
                return this.readValue(content, new TypeReference<>() {
                });
            } catch (IOException ioe) {
                throw new CompletionException(ioe);
            }
        }
    }
}
