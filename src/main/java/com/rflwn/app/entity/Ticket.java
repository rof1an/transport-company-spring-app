package com.rflwn.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

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

    private int ticketNumber;
    private Date purchaseDate;
    private String ticketType;
    private int ticketPrice;
    private String buyerFirstname;
    private String buyerLastname;
}
