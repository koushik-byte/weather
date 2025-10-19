package com.example.weather;

public final class WeatherData {
    private final String cityName;
    private final String countryCode;
    private final Double temperatureCelsius;
    private final Integer humidityPercent;
    private final String weatherDescription;
    private final Double windSpeedMetersPerSecond;
    private final Integer cloudinessPercent;

    public WeatherData(
            String cityName,
            String countryCode,
            Double temperatureCelsius,
            Integer humidityPercent,
            String weatherDescription,
            Double windSpeedMetersPerSecond,
            Integer cloudinessPercent
    ) {
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.temperatureCelsius = temperatureCelsius;
        this.humidityPercent = humidityPercent;
        this.weatherDescription = weatherDescription;
        this.windSpeedMetersPerSecond = windSpeedMetersPerSecond;
        this.cloudinessPercent = cloudinessPercent;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public Integer getHumidityPercent() {
        return humidityPercent;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public Double getWindSpeedMetersPerSecond() {
        return windSpeedMetersPerSecond;
    }

    public Integer getCloudinessPercent() {
        return cloudinessPercent;
    }
}