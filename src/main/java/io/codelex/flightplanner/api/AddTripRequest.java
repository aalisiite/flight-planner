package io.codelex.flightplanner.api;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;


public class AddTripRequest {
    private final Airport from;
    private final Airport to;
    private final String carrier;
    private final LocalDateTime departure;
    private final LocalDateTime arrival;


    @JsonCreator
    public AddTripRequest(@JsonProperty("from") Airport from,
                          @JsonProperty("to") Airport to,
                          @JsonProperty("carrier") String carrier,
                          @JsonProperty("departure") LocalDateTime departure,
                          @JsonProperty("arrival") LocalDateTime arrival) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departure = departure;
        this.arrival = arrival;

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

    public LocalDateTime getDeparture() {
        return departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }
}
