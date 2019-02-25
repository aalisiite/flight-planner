package io.codelex.flightplanner;

import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class PublicTripsController {

    @Autowired
    private TripService tripService;

    @GetMapping("/flights/search")
    public List<Trip> search(String from, String to) {
        return null;
    }

    @PostMapping("/flights")
    public ResponseEntity<List<Trip>> findTrip(@RequestBody FindTripRequest request) {
      return null;
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Trip> findTripById(@PathVariable Long id) {
        return  null;

    }

}





