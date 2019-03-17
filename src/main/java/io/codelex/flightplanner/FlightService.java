package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.FindFlightRequest;
import io.codelex.flightplanner.api.Flight;

import java.util.List;

public interface FlightService {
    Flight addTrip(AddFlightRequest request);

    List<Flight> search(String from, String to);

    void clear();

    Flight findById(Long id);

    List<Flight> findFlight(FindFlightRequest request);

    void deleteById(Long id);
}
