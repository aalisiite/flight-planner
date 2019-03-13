package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class RepositoryFlightServiceTest {

    FlightRecordRepository flightRecordRepository = Mockito.mock(FlightRecordRepository.class);
    AirportRecordRepository airportRecordRepository = Mockito.mock(AirportRecordRepository.class);

    RepositoryFlightService service = new RepositoryFlightService(flightRecordRepository, airportRecordRepository);

    @Test
    void should_check_if_flight_is_saved() {
        //given
        AddFlightRequest addFlightRequest = new AddFlightRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Sweden", "Stockholm", "DXB"),
                "Rynair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(5)
        );
        Mockito.when(airportRecordRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(airportRecordRepository.save(Mockito.any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
        Mockito.when(flightRecordRepository.save(Mockito.any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);

        //when
        Flight flight = service.addTrip(addFlightRequest);
        //then
        Assertions.assertEquals(addFlightRequest.getCarrier(), flight.getCarrier());
        Assertions.assertEquals(addFlightRequest.getFrom(), flight.getFrom());
        Assertions.assertEquals(addFlightRequest.getTo(), flight.getTo());
        Assertions.assertEquals(addFlightRequest.getDepartureTime(), flight.getDepartureTime());
        Assertions.assertEquals(addFlightRequest.getArrivalTime(), flight.getArrivalTime());
    }

    @Test
    void should_check_if_flight_is_present() {
        //given
        AddFlightRequest addFlightRequest = new AddFlightRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Sweden", "Stockholm", "DXB"),
                "Rynair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(5)
        );
        Mockito.when(flightRecordRepository.isFlightPresent(
                addFlightRequest.getFrom().getAirport(),
                addFlightRequest.getTo().getAirport(),
                addFlightRequest.getDepartureTime(),
                addFlightRequest.getArrivalTime(),
                addFlightRequest.getCarrier()
        )).thenReturn(true);
        //then
        Assertions.assertThrows(IllegalStateException.class, () -> service.addTrip(addFlightRequest));
    }

    @Test
    void should_search_from_if_to_is_empty() {
        //given
        Mockito.when(flightRecordRepository.searchTo("")).thenReturn(new ArrayList<>());
        //when
        List<Flight> flights = service.search("RIX", "");
        //then
        Assertions.assertEquals(0, flights.size());
    }
    @Test
    void should_search_to_if_from_is_empty(){
        //given
        Mockito.when(flightRecordRepository.searchFrom("")).thenReturn(new ArrayList<>());
        //when
        List<Flight> flights = service.search("", "RIX");
        //then
        Assertions.assertEquals(0, flights.size());
    }
}