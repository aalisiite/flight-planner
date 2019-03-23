package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class FlightWithWeather extends Flight {
    private final Weather weather;

    @JsonCreator
    public FlightWithWeather(@JsonProperty("id") Long id,
                             @JsonProperty("from") Airport from,
                             @JsonProperty("to") Airport to,
                             @JsonProperty("carrier") String carrier,
                             @JsonProperty("departureTime") LocalDateTime departureTime,
                             @JsonProperty("arrivalTime") LocalDateTime arrivalTime,
                             @JsonProperty("weather") Weather weather) {
        super(id, from, to, carrier, departureTime, arrivalTime);
        this.weather = weather;
    }

    public Weather getWeather() {
        return weather;
    }
}
