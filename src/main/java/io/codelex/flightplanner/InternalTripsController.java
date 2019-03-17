package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/internal-api")
class InternalTripsController {

    @Autowired
    private FlightService flightService;

    @PutMapping("/flights")
    public ResponseEntity<Flight> addTrip(@Valid @RequestBody AddFlightRequest request) {
        try {
            if (request.getDepartureTime().equals(request.getArrivalTime())
                    || request.getDepartureTime().isAfter(request.getArrivalTime())) {
                throw new IllegalArgumentException();
            }
            return new ResponseEntity<>(flightService.addTrip(request), HttpStatus.CREATED);
        } catch (NullPointerException | IllegalArgumentException o_o) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException k) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/flights/{id}")
    public void deleteTripById(@PathVariable Long id) {
        flightService.deleteById(id);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> findDeletedTip(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(flightService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException k) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/clear")
    public void deleteAll() {
        flightService.clear();
    }

}
