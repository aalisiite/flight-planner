package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class TripService {
    private final List<Trip> trips = new ArrayList<>();
    private Long sequence = 1L;

    Trip addTrip(AddTripRequest request) {
        if (isFlightPresent(request)) {
            throw new IllegalStateException();
        }


        Trip trip = new Trip(
                sequence++,
                request.getFrom(),
                request.getTo(),
                request.getCarrier(),
                request.getDeparture(),
                request.getArrival());
        trips.add(trip);
        return trip;

    }

    private boolean isFlightPresent(AddTripRequest request) {
        for (Trip trip : trips) {
            if (trip.getFrom().equals(request.getFrom())
                    && trip.getTo().equals(request.getTo())) {
                return true;
            }
        }
        return false;
    }

    List<Trip> search(String from, String to) {
        return trips.stream()
                .filter(trip -> isAirportMatching(trip.getFrom(), from) || isAirportMatching(trip.getTo(), to))
                .collect(Collectors.toList());

    }

    List<Trip> isAnyNulls(Trip trip) {
/*return trips.stream()
        .filter(trip->)*/
return null;
    }


    List<Trip> findAll() {

        return trips;
    }

    private boolean isAirportMatching(Airport airport, String search) {
        if (search != null && search.length() > 0) {
            if (airport.getCountry().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
            if (airport.getCity().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
            if (airport.getAirport().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
        }
        return false;

    }

    void clear() {
        trips.clear();
    }

    Trip findById(Long id) {
        for (Trip trip : trips) {
            if (trip.getId().equals(id)) {
                return trip;
            }
        }
        return null;
    }

    List<Trip> findRequest(FindTripRequest request) {
        return null;
    }

    void deleteById(Long id) {
        trips.removeIf(trip -> id.equals(trip.getId()));
    }
}
