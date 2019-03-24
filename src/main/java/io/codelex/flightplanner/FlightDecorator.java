package io.codelex.flightplanner;

import io.codelex.flightplanner.api.*;
import io.codelex.flightplanner.weather.ForecastCache;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
class FlightDecorator {


    private FlightService flightService;

    private ForecastCache forecastCache;

    public FlightDecorator(FlightService flightService, ForecastCache forecastCache) {
        this.flightService = flightService;
        this.forecastCache = forecastCache;
    }

    List<FlightWithWeather> findFlight(FindFlightRequest request) {
        List<Flight> flights = flightService.findFlight(request);
        List<FlightWithWeather> result = new ArrayList<>();
        for (Flight flight : flights) {
            result.add(decorate(flight));
        }
        return result;
    }

    private FlightWithWeather decorate(Flight flight) {
        Airport to = flight.getTo();

        Optional<Weather> weather = forecastCache.fetchForecast(to.getCity(), flight.getArrivalTime().toLocalDate());

        return new FlightWithWeather(
                flight.getId(),
                flight.getFrom(),
                to,
                flight.getCarrier(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                weather.orElse(null)
        );
    }

}
