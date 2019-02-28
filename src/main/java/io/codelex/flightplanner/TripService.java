package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Component
class TripService {
    private final List<Trip> trips = new ArrayList<>();
    private Long sequence = 1L;

    synchronized Trip addTrip(AddTripRequest request) {

        if (isAddRequestFieldsInvalid(request) || isAirportInvalid(request.getFrom()) || isAirportInvalid(request.getTo())) {
            throw new NullPointerException();
        }

        if (isFlightPresent(request)) {
            throw new IllegalStateException();
        }

        if (request.getFrom().getCity().toLowerCase().trim().equals(request.getTo().getCity().toLowerCase().trim())
                && request.getFrom().getCountry().toLowerCase().trim().equals(request.getTo().getCountry().toLowerCase().trim())
                && request.getFrom().getAirport().toLowerCase().trim().equals(request.getTo().getAirport().toLowerCase().trim())) {
            throw new IllegalArgumentException();
        }
        if (request.getDepartureTime().equals(request.getArrivalTime())
                || request.getDepartureTime().isAfter(request.getArrivalTime())) {
            throw new IllegalArgumentException();
        }

        Trip trip = new Trip(
                sequence++,
                request.getFrom(),
                request.getTo(),
                request.getCarrier(),
                request.getDepartureTime(),
                request.getArrivalTime());
        trips.add(trip);
        return trip;
    }

    private boolean isRequestFieldsInvalid(FindTripRequest request) {
        return request.getFrom() == null
                || request.getTo() == null
                || request.getDeparture() == null
                || request.getArrival() == null;
    }

    private boolean isAddRequestFieldsInvalid(AddTripRequest request) {
        return request.getFrom() == null
                || request.getTo() == null
                || request.getCarrier().length() == 0
                || request.getDepartureTime() == null
                || request.getArrivalTime() == null;
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

    private boolean isAirportMatching(Airport airport, String search) {
        if (search != null && search.length() > 0) {
            if (airport.getCountry().toLowerCase().contains(search.toLowerCase().trim())) {
                return true;
            }
            if (airport.getCity().toLowerCase().contains(search.toLowerCase().trim())) {
                return true;
            }
            if (airport.getAirport().toLowerCase().contains(search.toLowerCase().trim())) {
                return true;
            }
        }
        return false;
    }

    void clear() {
        trips.clear();
    }

    Trip findById(Long id) {
        return trips.stream()
                .filter(trip -> trip.getId().equals(id))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    List<Trip> findFlights(FindTripRequest request) {
        if (isRequestFieldsInvalid(request) || isAirportInvalid(request.getFrom()) || isAirportInvalid(request.getTo())
        ) {
            throw new NullPointerException();
        }

        if (request.getFrom().equals(request.getTo())) {
            throw new IllegalStateException();
        }
        return trips.stream()
                .filter(trip -> trip.getFrom().equals(request.getFrom())
                        && trip.getTo().equals(request.getTo())
                        && trip.getDepartureTime().toLocalDate().equals(request.getDeparture())
                        && trip.getArrivalTime().toLocalDate().equals(request.getArrival()))
                .collect(Collectors.toList());
    }

    private boolean isAirportInvalid(Airport airport) {
        return airport.getAirport().length() == 0
                || airport.getCountry().length() == 0
                || airport.getCity().length() == 0;
    }

    synchronized void deleteById(Long id) {
        trips.removeIf(trip -> id.equals(trip.getId()));
    }

    List<Trip> findAll() {
        return trips;
    }
}
