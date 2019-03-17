package io.codelex.flightplanner.repository.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class FlightRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AirportRecord from;
   
    @ManyToOne
    private AirportRecord to;

    private String carrier;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AirportRecord getFrom() {
        return from;
    }

    public void setFrom(AirportRecord from) {
        this.from = from;
    }

    public AirportRecord getTo() {
        return to;
    }

    public void setTo(AirportRecord to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
