package com.rflwn.app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String departurePoint;
    private String destinationPoint;
    private int travelTime;
    private String frequency;
    private int adultTicketPrice;
    private int childTicketPrice;
}
