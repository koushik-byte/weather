package com.example.weather;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.w3c.dom.Document;

public final class OpenWeatherMapClient {
    private final String apiKey;

    public OpenWeatherMapClient(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("OpenWeatherMap API key is required");
        }
        this.apiKey = apiKey;
    }

    public WeatherData getCurrentWeatherByCity(String cityQuery) throws IOException {
        // Example endpoint (XML): http://api.openweathermap.org/data/2.5/weather?q=London&mode=xml&units=metric&appid=API_KEY
        String encodedCity = URLEncoder.encode(cityQuery, StandardCharsets.UTF_8);
        String urlStr = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&mode=xml&units=metric&appid=%s",
                encodedCity,
                apiKey
        );
        HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(20000);

        int status = connection.getResponseCode();
        InputStream stream = status >= 200 && status < 300 ? connection.getInputStream() : connection.getErrorStream();
        if (stream == null) {
            throw new IOException("No response from OpenWeatherMap, HTTP status: " + status);
        }

        try (InputStream input = stream) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            dbf.setIgnoringComments(true);
            dbf.setIgnoringElementContentWhitespace(true);
            try {
                DocumentBuilder builder = dbf.newDocumentBuilder();
                Document doc = builder.parse(input);
                if (status >= 200 && status < 300) {
                    return new XmlWeatherParser().parse(doc);
                } else {
                    String message = doc.getDocumentElement() != null ? doc.getDocumentElement().getTextContent() : "unknown error";
                    throw new IOException("OpenWeatherMap error (HTTP " + status + "): " + message);
                }
            } catch (Exception e) {
                throw new IOException("Failed to parse OpenWeatherMap response", e);
            }
        } finally {
            connection.disconnect();
        }
    }
}