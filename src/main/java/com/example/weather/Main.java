package com.example.weather;

public final class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: java -cp out com.example.weather.Main <city name>\n" +
                    "Environment: set OWM_API_KEY to your OpenWeatherMap API key");
            System.exit(2);
        }
        String city = String.join(" ", args);
        String apiKey = System.getenv("OWM_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("Missing environment variable OWM_API_KEY");
            System.exit(3);
        }

        OpenWeatherMapClient client = new OpenWeatherMapClient(apiKey);
        WeatherData data = client.getCurrentWeatherByCity(city);

        printWeather(data);
    }

    private static void printWeather(WeatherData d) {
        System.out.println("=== Current Weather ===");
        System.out.printf("Location: %s, %s%n", nullToDash(d.getCityName()), nullToDash(d.getCountryCode()));
        System.out.printf("Temperature: %s °C%n", formatDouble(d.getTemperatureCelsius()));
        System.out.printf("Humidity: %s %% %n", formatInt(d.getHumidityPercent()));
        System.out.printf("Conditions: %s%n", nullToDash(d.getWeatherDescription()));
        System.out.printf("Wind: %s m/s%n", formatDouble(d.getWindSpeedMetersPerSecond()));
        System.out.printf("Cloudiness: %s %% %n", formatInt(d.getCloudinessPercent()));
    }

    private static String nullToDash(String s) { return s == null ? "-" : s; }
    private static String formatInt(Integer i) { return i == null ? "-" : Integer.toString(i); }
    private static String formatDouble(Double d) { return d == null ? "-" : String.format("%.1f", d); }
}
