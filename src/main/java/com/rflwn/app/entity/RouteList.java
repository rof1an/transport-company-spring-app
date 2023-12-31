package com.rflwn.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "routeslist")
public class RouteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime departureDate;
    private LocalDateTime destinationDate;
    private String travel_time;
    private int purchasedTickets;
    private int remainingTickets;
    private int routesPerDay;

    @ManyToOne
    @JoinColumn(name = "transportId")
    private Transport transport;

    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driver;

    @OneToOne
    @JoinColumn(name = "routeId")
    private Route route;
}
