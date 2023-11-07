package com.rflwn.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "routeslist")
public class RouteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date departureDate;
    private Date destinationDate;
    private int purchasedTickets;
    private int remainingTickets;

    @ManyToOne
    @JoinColumn(name = "transportId")
    private Transport transport;

    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driver;
}
