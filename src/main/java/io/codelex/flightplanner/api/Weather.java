package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Weather {
    @NotNull
    private final double temperature;
    @NotNull
    private final double precipitation;
    @NotNull
    private final double windSpeed;
    @NotEmpty
    private final String weatherCondition;

    @JsonCreator
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

   