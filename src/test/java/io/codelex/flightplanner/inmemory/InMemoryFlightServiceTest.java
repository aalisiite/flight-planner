package io.codelex.flightplanner.inmemory;

import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindFlightRequest;
import io.codelex.flightplanner.api.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFlightServiceTest {
    private InMemoryFlightService service = new InMemoryFlightService();

    @BeforeEach
    void setup() {
        service.clear();
    }


    @Test
    void should_Be_Able_To_Add_Flight() {
        //given
        AddFlightRequest request = createRequest();
        //when
        Flight flight = service.addTrip(request);

        //then
        Assertions.assertEquals(flight.getFrom(), request.getFrom());
        Assertions.assertEquals(flight.getTo(), request.getTo());
        Assertions.assertEquals(flight.getDepartureTime(), request.getDepartureTime());
        Assertions.assertEquals(flight.getArrivalTime(), request.getArrivalTime());
    }

    @Test
    void should_increment_id_when_adding_new_flight() {
        //given
        AddFlightRequest request = createRequest();
        Flight firstFlight = service.addTrip(request);
        //when
        AddFlightRequest request1 = new AddFlightRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Emirates", "Dubai", "DXB"),
                "Ryanair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(5)
        );
        Flight secondFlight = service.addTrip(request1);
        //then
        assertEquals(firstFlight.getId() + 1, secondFlight.getId());
    }

    @Test
    void should_be_able_to_get_added_flight_by_id() {
        //given
        AddFlightRequest request = createRequest();

        //when
        Flight getFlightById = service.addTrip(request);
        Flight result = service.findById(getFlightById.getId());
        //then
        assertNotNull(result);
        //and
        Assertions.assertEquals(result.getFrom(), request.getFrom());
        Assertions.assertEquals(result.getTo(), request.getTo());
        Assertions.assertEquals(result.getDepartureTime(), request.getDepartureTime());
        Assertions.assertEquals(result.getArrivalTime(), request.getArrivalTime());
        //when

    }

    @Test
    void should_not_be_able_to_add_duplicated_flight() {
        //given
        AddFlightRequest request = createRequest();
        //when
        service.addTrip(request);
        //then
        Assertions.assertThrows(IllegalStateException.class, () ->
                service.addTrip(request)
        );
    }

    @Test
    void should_be_able_to_delete_flight_by_id() {
        //given
        AddFlightRequest request = createRequest();
        Flight flight = service.addTrip(request);
        //when
        service.deleteById(flight.getId());
        //then
        Assertions.assertThrows(NoSuchElementException.class, () ->
                service.findById(flight.getId())
        );
    }
    
    @Test
    void should_not_find_flights_when_nulls_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search(null, null);
        //then;
        Assertions.assertTrue(flights.isEmpty());
    }

    @Test
    void should_find_flight_where_full_airport_name_from_passed() {

        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("RIX", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_full_country_from_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("Latvia", null);
        //then
        Assertions.assertEquals(1, flights.size());

    }

    @Test
    void should_find_flight_where_full_city_from_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("Riga", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_partial_airport_name_from_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("Rig", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_partial_country_from_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("Lat", null);
        //then


        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_partial_city_from_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("Rig", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_partial_lowercase_airport_name_from_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("ri", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_partial_uppercase_country_from_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("LAT", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_partial_uppercase_city_from_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("RI", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_airport_name_from_with_space_at_the_end_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("RIX ", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_partial_country_from_with_space_at_the_end_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("Latvia ", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_where_partial_city_from_with_space_at_the_end_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("RIX ", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_find_flight_request_where_full_city_name_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.search("Riga", null);
        //then
        Assertions.assertEquals(1, flights.size());
    }

    @Test
    void should_not_find_flight_if_no_matching_dates_present() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.findFlight(new FindFlightRequest(request.getFrom(), request.getTo(), LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 1)));
        //then
        Assertions.assertEquals(0, flights.size());
    }

    @Test
    void should_not_found_flight_when_nulls_passed() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.findFlight(new FindFlightRequest(request.getFrom(), request.getTo(), LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 1)));
        //then
        Assertions.assertEquals(0, flights.size());
    }

    @Test
    void should_not_find_matching_flight() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Flight> flights = service.findFlight(new FindFlightRequest(
                new Airport("Ru", "Moskow", "123"),
                new Airport("Tu", "uuuu", "234"),
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2019, 1, 1)));
        //then
        Assertions.assertTrue(flights.isEmpty());

    }

    @Test
    void should_not_find_when_from_and_to_are_equal() {
        //given
        AddFlightRequest request = createRequest();
        service.addTrip(request);
        //when

        //then
        Assertions.assertThrows(IllegalStateException.class, () ->
                service.findFlight(new FindFlightRequest(
                        new Airport("Latvia", "Riga", "RIX"),
                        new Airport("Latvia", "Riga", "RIX"),
                        LocalDate.of(2019, 1, 1),
                        LocalDate.of(2019, 2, 2))));
    }

    private AddFlightRequest createRequest() {
        return new AddFlightRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Sweden", "Stockholm", "ARN"),
                "Ryanair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(5)
        );
    }

}

