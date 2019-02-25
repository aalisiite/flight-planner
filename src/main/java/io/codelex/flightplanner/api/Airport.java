package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Airport {

    private String country;
    private String city;
    private String airport;

    @JsonCreator
    public Airport(@JsonProperty("country") String country,
                   @JsonProperty("city") String city,
                   @JsonProperty("airport") String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAirport() {
        return airport;
    }

}
