package com.akihabara.market.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.Properties;

import com.google.gson.*;

public class LlmService {
    private final String apiKey;

    public LlmService() {
        this.apiKey = cargarApiKey();
    }

    private String cargarApiKey() {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) { //Archivo del config
            prop.load(input);
            return prop.getProperty("OPENROUTER_API_KEY");
        } catch (IOException e) {
            System.err.println("Error al cargar la clave API: " + e.getMessage());
            return null;
        }
    }

    public String sugerirNombreProducto(String tipo, String franquicia) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "Error: clave API no cargada.";
        }

        String prompt = "Sugiere un nombre llamativo y original para un producto otaku del tipo '" + tipo +
                "' basado en la franquicia '" + franquicia + "'.";

        try {
            HttpClient client = HttpClient.newHttpClient();
            JsonObject message = new JsonObject();
            message.addProperty("role", "user");
            message.addProperty("content", prompt);

            JsonArray messages = new JsonArray();
            messages.add(message);

            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "mistralai/mistral-7b-instruct:free");
            requestBody.add("messages", messages);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://openrouter.ai/api/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            if (json.has("choices") && json.get("choices").isJsonArray()) {
                JsonArray choices = json.getAsJsonArray("choices");
                if (choices.size() > 0) {
                    JsonObject firstChoice = choices.get(0).getAsJsonObject();
                    if (firstChoice.has("message")) {
                        JsonObject messageObj = firstChoice.getAsJsonObject("message");
                        if (messageObj.has("content")) {
                            return messageObj.get("content").getAsString();
                        }
                    }
                }
            }

            return "Error: respuesta inesperada de la API.";

        } catch (Exception e) {
            return "Error al comunicarse con la API: " + e.getMessage();
        }
    }
}

