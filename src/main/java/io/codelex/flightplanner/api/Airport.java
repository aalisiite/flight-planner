package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class Airport {
    @NotEmpty
    private String country;
    @NotEmpty
    private String city;
    @NotEmpty
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return airport.equals(airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airport);
    }
}
