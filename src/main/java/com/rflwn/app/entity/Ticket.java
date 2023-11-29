package com.rflwn.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "routeId")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "routeListId")
    private RouteList routeList;

    private int ticketNumber;
    private LocalDateTime purchaseDate;
    private String ticketType;
    private int ticketPrice;
    private String buyerFirstname;
    private String buyerLastname;
    private String buyerPatronymic;

    public Ticket() {
        this.purchaseDate = LocalDateTime.now();
    }
}
