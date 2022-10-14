package servers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    public HttpClient client;
    private String apiToken;
    protected URI url;
    public KVTaskClient(String url) {
        this.client = HttpClient.newHttpClient();
        this.url = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/register"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                this.apiToken = response.body();
            } else System.out.println("Не удалось получить API_TOKEN");
        } catch (IOException | InterruptedException | NullPointerException e) {
            System.out.println("Ошибка регистрации");
        }
    }

    public void put(String key, String json)  {
        if (apiToken == null) {
            System.out.println("API_TOKEN не присвоен");
            return;
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/save/" + key + "?API_TOKEN=" + apiToken))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            if (response.statusCode() == 200) {
                System.out.println("Метод put успешно отработал");
            } else {
                System.out.println("Ошибка в методе put: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | NullPointerException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.");
        }
    }

    public String load(String key) {
        String statusToLoad = null;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/load" + key + "?API_TOKEN=" + apiToken))
                .GET()
                .build();
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                statusToLoad = response.body();
            } else {
                System.out.println("Ошибка в методе load: " + response.statusCode());
            }
        } catch (NullPointerException | InterruptedException | IOException e) {
            System.out.println("Во время выполнения запроса load возникла ошибка.");
        }
        return statusToLoad;
    }
}
