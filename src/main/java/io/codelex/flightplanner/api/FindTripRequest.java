package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class FindTripRequest {
    private Airport from;
    private Airport to;
    private LocalDate departure;
    private LocalDate arrival;

    @JsonCreator
    public FindTripRequest(@JsonProperty("from") Airport from,
                           @JsonProperty("to") Airport to,
                           @JsonProperty("departure") LocalDate departure,
                           @JsonProperty("arrival") LocalDate arrival) {
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arrival = arrival;
    }


    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public LocalDate getArrival() {
        return arrival;
    }


}
