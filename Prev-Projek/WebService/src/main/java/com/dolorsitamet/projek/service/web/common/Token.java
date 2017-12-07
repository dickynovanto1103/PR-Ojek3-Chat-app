package com.dolorsitamet.projek.service.web.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.servlet.ServletException;

/**
 * Token remote model class.
 */
public class Token {

  private static final String API_URL = "http://localhost:8080/IdentityService";

  public long id;
  public String name;

  /**
   * Create model from specified JSON object.
   *
   * @param json JSON object.
   */
  private Token(JsonObject json) {
    id = json.getInt("user_id");
    name = json.getString("user_name");
  }

  /**
   * Get model from specified token.
   *
   * @param token specified token.
   * @return token model.
   * @throws IOException if there's error while fetching data.
   * @throws ServletException if there's error in app. logic.
   */
  public static Token fromToken(String token) throws IOException, ServletException {
    HashMap<String, String> data = new HashMap<>();
    data.put("token", token);

    JsonStructure response = request(data);
    return (response != null) ? new Token((JsonObject) response) : null;
  }

  /**
   * Process request to identity service REST API.
   *
   * @param data query string to pass.
   * @return output payload.
   * @throws IOException if there's error while fetching data.
   * @throws ServletException if there's error in app. logic.
   */
  private static JsonStructure request(Map<String, String> data) throws IOException,
      ServletException {
    StringBuilder path = new StringBuilder(API_URL);
    path.append("/token?");

    // Prepare URL data.

    for (Map.Entry<String, String> entry : data.entrySet()) {
      path.append(entry.getKey());
      path.append('=');
      path.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
      path.append('&');
    }

    path.setLength(path.length() - 1);

    // Send request.

    HttpURLConnection request = (HttpURLConnection) new URL(path.toString())
        .openConnection();

    request.setRequestMethod("GET");
    request.setRequestProperty("Accept", "application/json");

    // Handle response.

    int responseCode = request.getResponseCode();

    JsonReader reader = Json.createReader(request.getInputStream());
    JsonStructure json = reader.read();
    reader.close();

    if (responseCode == 500) {
      JsonObject obj = (JsonObject) json;
      String message = obj.getString("error_message");

      throw new ServletException(message);
    }

    return json;
  }
}
