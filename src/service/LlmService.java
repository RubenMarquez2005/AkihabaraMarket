package service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class LlmService {
    private String apiKey; // Guarda la clave API

    public LlmService() {
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            apiKey = prop.getProperty("OPENROUTER_API_KEY"); // Leo la API key desde archivo
        } catch (IOException e) {
            e.printStackTrace(); // Si falla, muestro error
        }
    }

    public String sugerirNombreProducto(String tipo, String franquicia) {
        try {
            // Creo el texto para pedir sugerencia al modelo
            String prompt = "Sugiere un nombre llamativo y original para un producto otaku del tipo '" +
                    tipo + "' basado en la franquicia '" + franquicia + "'.";

            URL url = new URL("https://openrouter.ai/api/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST"); // Método POST
            conn.setRequestProperty("Authorization", "Bearer " + apiKey); // Autenticación
            conn.setRequestProperty("Content-Type", "application/json"); // Tipo JSON
            conn.setDoOutput(true); // Para enviar datos

            // Cuerpo del mensaje JSON con el prompt
            String body = "{\n" +
                    "  \"model\": \"openai/gpt-3.5-turbo\",\n" +
                    "  \"messages\": [\n" +
                    "    {\"role\": \"user\", \"content\": \"" + prompt + "\"}\n" +
                    "  ]\n" +
                    "}";

            // Enviar cuerpo al servidor
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Leer respuesta del servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Parseo básico para sacar solo el texto de la respuesta
            String json = response.toString();
            int contentIndex = json.indexOf("\"content\":\"");
            if (contentIndex == -1) return "No se pudo obtener una sugerencia.";

            int start = contentIndex + 11;
            int end = json.indexOf("\"", start);

            // Corregir por comillas escapadas
            while (end != -1 && json.charAt(end - 1) == '\\') {
                end = json.indexOf("\"", end + 1);
            }

            String respuesta = json.substring(start, end);
            return respuesta.replace("\\n", "").replace("\\", "").trim(); // Limpio texto y devuelvo

        } catch (Exception e) {
            e.printStackTrace(); // Error en conexión o parseo
            return "Error al conectar con el modelo.";
        }
    }
}
