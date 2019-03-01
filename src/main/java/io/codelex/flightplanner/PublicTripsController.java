package io.codelex.flightplanner;

import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
class PublicTripsController {

    @Autowired
    private TripService tripService;

    @GetMapping("/flights/search")
    public List<Trip> search(String from, String to) {
        return tripService.search(from, to);
    }

    @PostMapping("/flights")
    public ResponseEntity<List<Trip>> findTrip(@RequestBody FindTripRequest request) {
        try {
            return new ResponseEntity<>(tripService.findFlights(request), HttpStatus.OK);
        } catch (NullPointerException | IllegalStateException o_O) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Trip> findTripById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(tripService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException o) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}





