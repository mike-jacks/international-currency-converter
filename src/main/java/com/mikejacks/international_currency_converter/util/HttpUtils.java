package com.mikejacks.international_currency_converter.util;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUtils {

    /**
     * Makes an HTTP GET request to the specified URL and returns the JSON response as a string.
     *
     * @param urlString The URL string for the HTTP GET request.
     * @return The JSON response as a string.
     * @throws Exception If an error occurs during the HTTP request.
     */
    public static @NotNull String getJsonResponse(String urlString) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(urlString)).header("Content-Type", "application/json").build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
        }

        return response.body();
    }

    /**
     * Parses a JSON response string to extract the data object.
     *
     * @param jsonResponse The JSON response string.
     * @return A {@code JSONObject} representing the data object.
     * @throws Exception If an error occurs during JSON parsing.
     */
    public static JSONObject parseJsonResponse(String jsonResponse) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getJSONObject("data");
    }
}
