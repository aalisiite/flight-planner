package io.codelex.flightplanner.api;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class AddFlightRequest {
    @Valid
    @NotNull
    private final Airport from;
    @Valid
    @NotNull
    private final Airport to;
    @NotEmpty
    private final String carrier;
    @NotNull
    private final LocalDateTime departureTime;
    @NotNull
    private final LocalDateTime arrivalTime;


    @JsonCreator
    public AddFlightRequest(@JsonProperty("from") Airport from,
                            @JsonProperty("to") Airport to,
                            @JsonProperty("carrier") String carrier,
                            @JsonProperty("departureTime") LocalDateTime departure,
                            @JsonProperty("arrivalTime") LocalDateTime arrival) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departure;
        this.arrivalTime = arrival;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public String getCarrier() {
        return carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
