package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest {
    private TripService service = new TripService();

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
        Assertions.assertEquals(trip.getDepartureTime(), request.getDepartureTime());
        Assertions.assertEquals(trip.getArrivalTime(), request.getArrivalTime());
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
        Trip secondFlight = service.addTrip(request1);

        //then
        assertEquals(firstFlight.getId() + 1, secondFlight.getId());
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
        Assertions.assertEquals(result.getDepartureTime(), request.getDepartureTime());
        Assertions.assertEquals(result.getArrivalTime(), request.getArrivalTime());
        //when

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
        service.addTrip(request);
        //when
        service.clear();
        //then
        Assertions.assertEquals(service.findAll().size(), 0);
    }


    @Test
    void should_not_find_flights_when_nulls_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search(null, null);
        //then;
        Assertions.assertTrue(trips.isEmpty());
    }

    @Test
    void should_find_flight_where_full_airport_name_from_passed() {

        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("RIX", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_full_country_from_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("Latvia", null);
        //then
        Assertions.assertEquals(1, trips.size());

    }

    @Test
    void should_find_flight_where_full_city_from_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("Riga", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_partial_airport_name_from_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("Riga", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_partial_country_from_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("Lat", null);
        //then


        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_partial_city_from_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("Rig", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_partial_lowercase_airport_name_from_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("ri", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_partial_uppercase_country_from_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("LAT", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_partial_uppercase_city_from_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("RI", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_airport_name_from_with_space_at_the_end_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("RIX ", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_partial_country_from_with_space_at_the_end_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("Latvia ", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_where_partial_city_from_with_space_at_the_end_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.search("RIX ", null);
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_find_flight_request_where_full_city_name_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.findFlights(createRequestOther());
        //then
        Assertions.assertEquals(1, trips.size());
    }

    @Test
    void should_not_find_flight_if_no_matching_dates_present() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.findFlights(new FindTripRequest(request.getFrom(), request.getTo(), LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 1)));
        //then
        Assertions.assertEquals(0, trips.size());
    }

    @Test
    void should_not_found_flight_when_nulls_passed() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.findFlights(new FindTripRequest(request.getFrom(), request.getTo(), LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 1)));
        //then
        Assertions.assertEquals(0, trips.size());
    }

    @Test
    void should_not_find_matching_flight() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.findFlights(new FindTripRequest(
                new Airport("Ru", "Moskow", "123"),
                new Airport("Tu", "uuuu", "234"),
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2019, 1, 1)));
        //then
        Assertions.assertTrue(trips.isEmpty());

    }

    @Test
    void should_not_find_when_from_and_to_are_equal() {
        //given
        AddTripRequest request = createRequest();
        service.addTrip(request);
        //when
        List<Trip> trips = service.findFlights(new FindTripRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Latvia", "Riga", "RIX"),
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2019, 2, 2)));
        //then
        Assertions.assertEquals(trips.size(), 0);
    }
    
    private AddTripRequest createRequest() {
        return new AddTripRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Sweden", "Stockholm", "ARN"),
                "Ryanair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(5)
        );
    }

    private FindTripRequest createRequestOther() {
        return new FindTripRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Sweden", "Stockholm", "ARN"),
                LocalDate.now(),
                LocalDate.now().plusDays(10)
        );
    }
}

