package io.codelex.flightplanner;

import io.codelex.flightplanner.api.FindFlightRequest;
import io.codelex.flightplanner.api.Flight;
import io.codelex.flightplanner.api.FlightWithWeather;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
class PublicTripsController {

    private FlightService flightService;
    private FlightDecorator flightDecorator;

    public PublicTripsController(FlightService flightService, FlightDecorator flightDecorator) {
        this.flightService = flightService;
        this.flightDecorator = flightDecorator;
    }

    @GetMapping("/flights/search")
    public List<Flight> search(String from, String to) {
        if (from == null || to == null) {
            return Collections.emptyList();
        } else {
            return flightService.search(from, to);
        }
    }

    @PostMapping("/flights")
    public ResponseEntity<List<FlightWithWeather>> findTrip(@Valid @RequestBody FindFlightRequest request) {
        try {
            return new ResponseEntity<>(flightDecorator.findFlight(request), HttpStatus.OK);
        } catch (NullPointerException | IllegalStateException | IllegalArgumentException o_O) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> findTripById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(flightService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException o) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}





