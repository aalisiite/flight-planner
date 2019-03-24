package io.codelex.flightplanner.api;

public class Weather {

    private final double temperature;

    private final double precipitation;

    private final double windSpeed;

    private final String weatherCondition;

    public Weather(double temperature, double precipitation, double windSpeed, String weatherCondition) {
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.weatherCondition = weatherCondition;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }
}

   