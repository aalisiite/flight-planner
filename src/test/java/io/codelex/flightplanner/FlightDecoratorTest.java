package io.codelex.flightplanner;

import io.codelex.flightplanner.api.*;
import io.codelex.flightplanner.weather.ForecastCache;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightDecoratorTest {

    private FlightService flightService = mock(FlightService.class);
    private ForecastCache forecastCache = mock(ForecastCache.class);
    private FlightDecorator decorator = new FlightDecorator(
            flightService,
            forecastCache);
    private LocalDate date = LocalDate.of(2019, 10, 2);
    private Weather dafaultWeather = new Weather(0, 0, 0, "Snow");

    @Test
    void should_add_weather_when_city_flight_is_given() {
        //given
        FindFlightRequest request = new FindFlightRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("Sweden", "Stocholm", "ARN"),
                date,
                date.plusDays(1)
        );
        List<Flight> flightFromService = Arrays.asList(
                new Flight(
                        1L,
                        new Airport("Latvia", "Riga", "RIX"),
                        new Airport("Sweden", "Stocholm", "ARN"),
                        "Ryanair",
                        date.atStartOfDay(),
                        date.plusDays(1).atStartOfDay()
                )
        );
        when(forecastCache.fetchForecast("Stocholm", date.plusDays(1))).thenReturn(Optional.of(dafaultWeather));
        when(flightService.findFlight(request)).thenReturn(flightFromService);
        //when
        List<FlightWithWeather> flights = decorator.findFlight(request);
        //then
        assertEquals(1, flights.size());
        //when
        FlightWithWeather flight = flights.get(0);
        //then
        assertEquals(flight.getWeather().getWeatherCondition(), "Snow");
    }
}