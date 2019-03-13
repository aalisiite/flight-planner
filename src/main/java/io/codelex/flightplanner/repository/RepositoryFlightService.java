package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.FlightService;
import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindFlightRequest;
import io.codelex.flightplanner.api.Flight;
import io.codelex.flightplanner.repository.model.AirportRecord;
import io.codelex.flightplanner.repository.model.FlightRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
class RepositoryFlightService implements FlightService {
    private final FlightRecordRepository flightRecordRepository;
    private final AirportRecordRepository airportRecordRepository;
    private final MapFlightRecordToFlight toTrip = new MapFlightRecordToFlight();

    RepositoryFlightService(FlightRecordRepository flightRecordRepository, AirportRecordRepository airportRecordRepository) {
        this.flightRecordRepository = flightRecordRepository;
        this.airportRecordRepository = airportRecordRepository;
    }

    @Override
    public Flight addTrip(AddFlightRequest request) {

       /* if (isAddRequestFieldsInvalid(request) || isAirportInvalid(request.getFrom()) || isAirportInvalid(request.getTo())) {
            throw new NullPointerException();
        }*/
       

        if (flightRecordRepository.isFlightPresent(request.getFrom().getAirport(), request.getTo().getAirport(), request.getDepartureTime(), request.getArrivalTime(), request.getCarrier())) {
            throw new IllegalStateException();
        }
        if (request.getFrom().getAirport().toLowerCase().trim().equals(request.getTo().getAirport().toLowerCase().trim())) {
            throw new IllegalArgumentException();
        }

        FlightRecord flightRecord = new FlightRecord();
        flightRecord.setFrom(createOrGetAirport(request.getFrom()));
        flightRecord.setTo(createOrGetAirport(request.getTo()));
        flightRecord.setCarrier(request.getCarrier());
        flightRecord.setDepartureTime(request.getDepartureTime());
        flightRecord.setArrivalTime(request.getArrivalTime());

        flightRecord = flightRecordRepository.save(flightRecord);
        return toTrip.apply(flightRecord);
    }

   /* private boolean isRequestFieldsInvalid(FindFlightRequest request) {
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
    }*/

    private AirportRecord createOrGetAirport(Airport airport) {
        return airportRecordRepository.findById(airport.getAirport())
                .orElseGet(() -> {
                    AirportRecord created = new AirportRecord(
                            airport.getAirport(),
                            airport.getCity(),
                            airport.getCountry()
                    );
                    return airportRecordRepository.save(created);
                });
    }

    /*private boolean isAirportInvalid(Airport airport) {
        return airport.getAirport().length() == 0
                || airport.getCountry().length() == 0
                || airport.getCity().length() == 0;
    }*/

    @Override
    public List<Flight> search(String from, String to) {

        if (to.equals("")) {
            return flightRecordRepository.searchFrom(from)
                    .stream()
                    .map(toTrip)
                    .collect(Collectors.toList());
        }

        if (from.equals("")) {
            return flightRecordRepository.searchTo(to)
                    .stream()
                    .map(toTrip)
                    .collect(Collectors.toList());
        }

        return flightRecordRepository.searchFlights(from, to)
                .stream()
                .map(toTrip)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        flightRecordRepository.deleteAll();
    }

    @Override
    public Flight findById(Long id) {
        return flightRecordRepository.findById(id)
                .map(toTrip)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Flight> findFlight(FindFlightRequest request) {

        /*if (isRequestFieldsInvalid(request) || isAirportInvalid(request.getFrom()) || isAirportInvalid(request.getTo())
        ) {
            throw new NullPointerException();
        }*/

        if (request.getFrom().getAirport().equals(request.getTo().getAirport())) {
            throw new IllegalArgumentException();
        }

        return flightRecordRepository.findFlight(
                request.getFrom().getAirport(),
                request.getTo().getAirport(),
                request.getDeparture().atStartOfDay(),
                request.getDeparture().atStartOfDay().plusDays(1),
                request.getArrival().atStartOfDay(),
                request.getArrival().atStartOfDay().plusDays(1)
        ).stream().map(toTrip).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        try {
            flightRecordRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }

}
