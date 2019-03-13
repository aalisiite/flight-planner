package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class FindFlightRequest {
    @Valid
    @NotNull
    private Airport from;
    @Valid
    @NotNull
    private Airport to;
    @NotNull
    private LocalDate departure;
    @NotNull
    private LocalDate arrival;

    @JsonCreator
    public FindFlightRequest(@JsonProperty("from") Airport from,
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
