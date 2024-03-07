package lk.ijse.gdse.demo.util;

import lk.ijse.gdse.demo.controllers.InventoryController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static lk.ijse.gdse.demo.controllers.LoginController.jwtToken;

public class ConnectToBackend {

    public static HttpResponse<String> connectBackend(String url, String methodType, String requestBody) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", jwtToken);

            switch (methodType.toUpperCase()) {
                case "GET":
                    requestBuilder.GET();
                    break;
                case "POST":
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
                    break;
                case "PUT":
                    requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(requestBody));
                    break;
                case "DELETE":
                    requestBuilder.DELETE();
                    break;
                // Add more cases for other HTTP methods as needed

                default:
                    throw new IllegalArgumentException("Unsupported HTTP method: " + methodType);
            }

            HttpRequest request = requestBuilder.build();

            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return null; // Handle this according to your needs
        }
    }
}
