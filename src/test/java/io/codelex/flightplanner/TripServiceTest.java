package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.Trip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest {
    TripService service = new TripService();

    @BeforeEach
    void setup() {
        service.clear();
    }


    @Test
    void should_Be_Able_To_Add_Flight() {
        //given
        AddTripRequest request = createRequest();
        //when
        Trip trip = service.addTrip(request);

        //then
        Assertions.assertEquals(trip.getFrom(), request.getFrom());
        Assertions.assertEquals(trip.getTo(), request.getTo());
        Assertions.assertEquals(trip.getDepartureTime(), request.getDeparture());
        Assertions.assertEquals(trip.getArrivalTime(), request.getArrival());
    }

    @Test
    void should_increment_id_when_adding_new_flight() {
        //given
        AddTripRequest request = createRequest();

        //when
        Trip firstFlight = service.addTrip(request);

        AddTripRequest request1 = new AddTripRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Emirates", "Dubai", "DXB"),
                "Ryanair",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Trip secondfFlight = service.addTrip(request1);

        //then
        assertEquals(firstFlight.getId() + 1, secondfFlight.getId());
    }

    @Test
    void should_be_able_to_get_added_flight_by_id() {
        //given
        AddTripRequest request = createRequest();

        //when
        Trip getFlightById = service.addTrip(request);
        Trip result = service.findById(getFlightById.getId());
        //then
        assertNotNull(result);
        //and
        Assertions.assertEquals(result.getFrom(), request.getFrom());
        Assertions.assertEquals(result.getTo(), request.getTo());
        Assertions.assertEquals(result.getDepartureTime(), request.getDeparture());
        Assertions.assertEquals(result.getArrivalTime(), request.getArrival());
        //when
       /* Trip result = service.findById(777L);
        //then
        assertNotNull(result);*/
    }

    @Test
    void should_not_be_able_to_add_duplicated_flight() {

        //given
        AddTripRequest request = createRequest();

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
        AddTripRequest request = createRequest();
        //when
        Trip trip = service.addTrip(request);
        service.deleteById(trip.getId());

        //then
        trip = service.findById(trip.getId());
        assertNull(trip);
    }

    @Test
    void should_be_able_to_delete_all_flights() {
        //given
        AddTripRequest request = createRequest();
        //when
        Trip trip = service.addTrip(request);
        service.clear();

        //then

        assertNotNull(trip);
    }


    @Test
    void should_not_find_flights_when_nulls_passed() {
        //given
        AddTripRequest request = createRequest();
        //when
        Trip trip = service.addTrip(request);

        //then;

    }

    @Test
    void should_find_flight_where_full_airport_name_from_passed() {
    }

    @Test
    void should_find_flight_where_full_country_from_passed() {
    }

    @Test
    void should_find_flight_where_full_city_from_passed() {
    }

    @Test
    void should_find_flight_where_partial_airport_name_from_passed() {
    }

    @Test
    void should_find_flight_where_partial_country_from_passed() {
    }

    @Test
    void should_find_flight_where_partial_city_from_passed() {
    }

    @Test
    void should_find_flight_where_partial_lowercase_airport_name_from_passed() {
    }

    @Test
    void should_find_flight_where_partial_uppercase_country_from_passed() {
    }

    @Test
    void should_find_flight_where_partial_uppercase_city_from_passed() {
    }

    @Test
    void should_find_flight_where_airport_name_from_with_space_at_the_end_passed() {
    }

    @Test
    void should_find_flight_where_partial_country_from_with_space_at_the_end_passed() {
    }

    @Test
    void should_find_flight_where_partial_city_from_with_space_at_the_end_passed() {
    }

    private AddTripRequest createRequest() {
        AddTripRequest request = new AddTripRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Sweden", "Stockholm", "ARN"),
                "Ryanair",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return request;
    }
}

