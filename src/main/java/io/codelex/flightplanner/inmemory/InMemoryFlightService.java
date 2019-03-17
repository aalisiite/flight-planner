package io.codelex.flightplanner.inmemory;

import io.codelex.flightplanner.FlightService;
import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindFlightRequest;
import io.codelex.flightplanner.api.Flight;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Component
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "in-memory")
class InMemoryFlightService implements FlightService {
    private final List<Flight> flights = new ArrayList<>();
    private AtomicLong sequence = new AtomicLong(1L);

    @Override
    synchronized public Flight addTrip(AddFlightRequest request) {

        if (isAddRequestFieldsInvalid(request) || isAirportInvalid(request.getFrom()) || isAirportInvalid(request.getTo())) {
            throw new NullPointerException();
        }

        if (isFlightPresent(request)) {
            throw new IllegalStateException();
        }

        if (request.getFrom().getAirport().toLowerCase().trim().equals(request.getTo().getAirport().toLowerCase().trim())) {
            throw new IllegalArgumentException();
        }
        if (request.getDepartureTime().equals(request.getArrivalTime())
                || request.getDepartureTime().isAfter(request.getArrivalTime())) {
            throw new IllegalArgumentException();
        }

        Flight flight = new Flight(
                sequence.incrementAndGet(),
                request.getFrom(),
                request.getTo(),
                request.getCarrier(),
                request.getDepartureTime(),
                request.getArrivalTime());
        flights.add(flight);
        return flight;
    }

    private boolean isRequestFieldsInvalid(FindFlightRequest request) {
        return request.getFrom() == null
                || request.getTo() == null
                || request.getDeparture() == null
                || request.getArrival() == null;
    }

    private boolean isAddRequestFieldsInvalid(AddFlightRequest request) {
        return request.getFrom() == null
                || request.getTo() == null
                || request.getCarrier().length() == 0
                || request.getDepartureTime() == null
                || request.getArrivalTime() == null;
    }

    private boolean isFlightPresent(AddFlightRequest request) {
        for (Flight flight : flights) {
            if (flight.getFrom().equals(request.getFrom())
                    && flight.getTo().equals(request.getTo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Flight> search(String from, String to) {

        return flights.stream()
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

    @Override
    public void clear() {
        flights.clear();
    }

    @Override
    public Flight findById(Long id) {
        return flights.stream()
                .filter(trip -> trip.getId().equals(id))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Flight> findFlight(FindFlightRequest request) {
        if (isRequestFieldsInvalid(request) || isAirportInvalid(request.getFrom()) || isAirportInvalid(request.getTo())
        ) {
            throw new NullPointerException();
        }

        if (request.getFrom().equals(request.getTo())) {
            throw new IllegalStateException();
        }
        return flights.stream()
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

    @Override
    synchronized public void deleteById(Long id) {
        flights.removeIf(trip -> id.equals(trip.getId()));
    }
}