package com.rflwn.app.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RouteListDto {
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
    private TransportDto transport;

    @ManyToOne
    @JoinColumn(name = "driverId")
    private DriverDto driver;

    @OneToOne
    @JoinColumn(name = "routeId")
    private RouteDto route;
}
