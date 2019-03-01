package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/internal-api")
class InternalTripsController {

    @Autowired
    private TripService tripService;

    @PutMapping("/flights")
    public ResponseEntity<Trip> addTrip(@RequestBody AddTripRequest request) {
        try {
            return new ResponseEntity<>(tripService.addTrip(request), HttpStatus.CREATED);
        } catch (NullPointerException | IllegalArgumentException o_o) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException k) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/flights/{id}")
    public void deleteTripById(@PathVariable Long id) {
        tripService.deleteById(id);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Trip> findDeletedTip(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(tripService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException k) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/clear")
    public void deleteAll() {
        tripService.clear();
    }

}
