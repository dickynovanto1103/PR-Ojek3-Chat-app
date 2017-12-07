package com.dolorsitamet.projek.app.web.service;

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
import javax.servlet.ServletException;

public class Identity {

  private static final String API_URL = "http://localhost:8080/IdentityService";

  public String token;

  public long id;
  public String name;

  /**
   * Create model from specified JSON object.
   *
   * @param json JSON object.
   */
  private Identity(String token, JsonObject json) {
    this.token = token;

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
  public static Identity fromToken(String token) throws IOException, ServletException {
    HashMap<String, String> data = new HashMap<>();
    data.put("token", token);

    JsonObject response = request("GET", "/token", data);
    return (response != null) ? new Identity(token, response) : null;
  }

  /**
   * Create token from username and password.
   *
   * @param user username.
   * @param pass password.
   * @return token.
   * @throws IOException if there's error while fetching data.
   * @throws ServletException if there's error in app. logic.
   */
  public static String createToken(String user, String pass) throws IOException, ServletException {
    HashMap<String, String> data = new HashMap<>();
    data.put("username", user);
    data.put("password", pass);

    JsonObject response = request("POST", "/token", data);
    return response.getString("token");
  }

  /**
   * Delete token from database.
   *
   * @param token token.
   * @throws IOException if there's error while fetching data.
   * @throws ServletException if there's error in app. logic.
   */
  public static void deleteToken(String token) throws IOException, ServletException {
    HashMap<String, String> data = new HashMap<>();
    data.put("token", token);

    request("DELETE", "/token", data);
  }

  /**
   * Create account and token with username and password.
   *
   * @param user username.
   * @param pass password.
   * @return token.
   * @throws IOException if there's error while fetching data.
   * @throws ServletException if there's error in app. logic.
   */
  public static String createAccountAndToken(String user, String pass)
      throws IOException, ServletException {

    HashMap<String, String> data = new HashMap<>();
    data.put("username", user);
    data.put("password", pass);

    request("POST", "/account", data);
    return createToken(user, pass);
  }

  /**
   * Process request to identity service REST API.
   *
   * @param data query string to pass.
   * @return output payload.
   * @throws IOException if there's error while fetching data.
   * @throws ServletException if there's error in app. logic.
   */
  private static JsonObject request(String httpMethod, String httpUrl, Map<String, String> data)
      throws IOException, ServletException {

    StringBuilder path = new StringBuilder(API_URL);
    path.append(httpUrl);
    path.append('?');

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

    request.setRequestMethod(httpMethod);
    request.setRequestProperty("Accept", "application/json");

    // Handle response.

    JsonReader reader = Json.createReader(request.getInputStream());
    JsonObject json = reader.readObject();
    reader.close();

    if ("error".equals(json.getString("status"))) {
      String message = json.getString("error_message");

      throw new ServletException(message);
    }

    return json;
  }
}
