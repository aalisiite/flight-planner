package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.Flight;
import io.codelex.flightplanner.repository.model.FlightRecord;

import java.util.function.Function;

class MapFlightRecordToFlight implements Function<FlightRecord, Flight> {

    @Override
    public Flight apply(FlightRecord flightRecord) {
        return new Flight(
                flightRecord.getId(),
                new Airport(
                        flightRecord.getFrom().getCountry(),
                        flightRecord.getFrom().getCity(),
                        flightRecord.getFrom().getAirport()
                ),
                new Airport(
                        flightRecord.getTo().getCountry(),
                        flightRecord.getTo().getCity(),
                        flightRecord.getTo().getAirport()
                ),
                flightRecord.getCarrier(),
                flightRecord.getDepartureTime(),
                flightRecord.getArrivalTime()
        );

    }
}
