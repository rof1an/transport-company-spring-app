package com.rflwn.app.dto;

import com.rflwn.app.entity.Route;
import com.rflwn.app.entity.RouteList;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDto {
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

    public TicketDto() {
        this.purchaseDate = LocalDateTime.now();
    }
}
