package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal-api")
public class InternalTripsController {

    @Autowired
    private TripService tripService;

    @PutMapping("/flights")
    public ResponseEntity<Trip> addTrip(@RequestBody AddTripRequest request) {
        return null;
    }

    @DeleteMapping("/flights/{id}")
    public void deleteTripById(@PathVariable Long id) {

    }


}
