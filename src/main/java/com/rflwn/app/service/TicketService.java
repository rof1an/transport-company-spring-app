package com.rflwn.app.service;

import com.rflwn.app.entity.Route;
import com.rflwn.app.entity.RouteList;
import com.rflwn.app.entity.Ticket;
import com.rflwn.app.repostiory.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final RouteService routeService;
    private final RouteListService routeListService;
    private final TransportService transportService;
    private final TicketRepository ticketRepository;

    public List<Ticket> getAllTicketsInfo() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(int id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Билет с id = %d не найден", id)));
    }

    public List<Ticket> addNewTicketsList(List<Ticket> ticketList) {
        ticketList.forEach(route -> {
            RouteList routeListById = routeListService.getRouteListById(route.getRouteList().getId());
            routeListById.setPurchasedTickets(routeListById.getPurchasedTickets() + 1);
            routeListById.setRemainingTickets(routeListById.getRemainingTickets() - 1);
        });


        List<Integer> ticketsIds = ticketList.stream()
                .map(ticket -> ticket.getRoute().getId())
                .toList();

        for (Integer id : ticketsIds) {
            Route routeInfoById = routeService.getRouteById(id);

            List<String> ticketsTypeList = ticketList.stream()
                    .filter(ticket -> ticket.getRoute().getId() == id)
                    .map(Ticket::getTicketType)
                    .toList();

            ticketList.stream()
                    .filter(ticket -> ticket.getRoute().getId() == id)
                    .forEach(obj -> {
                        obj.setRoute(routeInfoById);

                        RouteList routeListById = routeListService.getRouteListById(obj.getRouteList().getId());
                        obj.setRouteList(routeListById);

                        if (ticketsTypeList.contains("взрослый")) {
                            obj.setTicketPrice(routeInfoById.getAdultTicketPrice());
                        }
                        if (ticketsTypeList.contains("детский")) {
                            obj.setTicketPrice(routeInfoById.getChildTicketPrice());
                        }
                    });
        }

        ticketList.forEach(ticket -> {
            int capacity = ticket.getRouteList().getTransport().getCapacity();
            int ticketsListSize = getAllTicketsInfo().size() + 1;

            if (ticketsListSize <= capacity) {
                ticketRepository.saveAll(ticketList);
            } else {
                throw new RuntimeException("Превышена вместимость транспорта");
            }
        });

        return ticketList;
    }

    public Ticket changeOneTicketInfo(Ticket existingTicket, Ticket newTicket) {
        String buyerFirstname = newTicket.getBuyerFirstname();
        if (!existingTicket.getBuyerFirstname().equalsIgnoreCase(buyerFirstname)) {
            existingTicket.setBuyerFirstname(buyerFirstname);
        }
        String buyerLastname = newTicket.getBuyerLastname();
        if (!existingTicket.getBuyerLastname().equalsIgnoreCase(buyerLastname)) {
            existingTicket.setBuyerLastname(buyerLastname);
        }

        int routeId = newTicket.getRoute().getId();
        Route newRouteById = routeService.getRouteById(routeId);
        if (!newTicket.getTicketType().equalsIgnoreCase(existingTicket.getTicketType())) {
            existingTicket.setTicketType(newTicket.getTicketType());
            if (newTicket.getTicketType().equalsIgnoreCase("взрослый")) {
                existingTicket.setTicketPrice(newRouteById.getAdultTicketPrice());
            }
            if (newTicket.getTicketType().equalsIgnoreCase("детский")) {
                existingTicket.setTicketPrice(newRouteById.getChildTicketPrice());
            }
        }

        if (existingTicket.getRoute().getId() != routeId) {
            existingTicket.setRoute(newRouteById);
            if (existingTicket.getTicketType().equalsIgnoreCase("взрослый")) {
                existingTicket.setTicketPrice(newRouteById.getAdultTicketPrice());
            }
            if (existingTicket.getTicketType().equalsIgnoreCase("детский")) {
                existingTicket.setTicketPrice(newRouteById.getChildTicketPrice());
            }
        }

        return ticketRepository.save(existingTicket);
    }

    public List<Ticket> changeAllTicketsInfo(List<Ticket> newTicketListInfo) {
        List<Ticket> updatedTickets = new ArrayList<>();

        for (Ticket newTicketInfo : newTicketListInfo) {
            Ticket existingTicket = getTicketById(newTicketInfo.getId());

            if (existingTicket != null) {
                if (!existingTicket.getBuyerFirstname().equals(newTicketInfo.getBuyerFirstname())) {
                    existingTicket.setBuyerFirstname(newTicketInfo.getBuyerFirstname());
                }
                if (!existingTicket.getBuyerLastname().equals(newTicketInfo.getBuyerLastname())) {
                    existingTicket.setBuyerLastname(newTicketInfo.getBuyerLastname());
                }

                String newTicketType = newTicketInfo.getTicketType();
                if (!existingTicket.getTicketType().equalsIgnoreCase(newTicketType)) {
                    existingTicket.setTicketType(newTicketType);

                    if (newTicketType.equalsIgnoreCase("взрослый")) {
                        existingTicket.setTicketPrice(existingTicket.getRoute().getAdultTicketPrice());
                    }
                    if (newTicketType.equalsIgnoreCase("детский")) {
                        existingTicket.setTicketPrice(existingTicket.getRoute().getChildTicketPrice());
                    }
                }

                int newTicketRouteId = newTicketInfo.getRoute().getId();
                if (newTicketRouteId != existingTicket.getRoute().getId()) {
                    Route newRouteInfoById = routeService.getRouteById(newTicketRouteId);
                    existingTicket.setRoute(newRouteInfoById);

                    if (newTicketType.equalsIgnoreCase("взрослый")) {
                        existingTicket.setTicketPrice(newRouteInfoById.getAdultTicketPrice());
                    }
                    if (newTicketType.equalsIgnoreCase("детский")) {
                        existingTicket.setTicketPrice(newRouteInfoById.getChildTicketPrice());
                    }
                }

                updatedTickets.add(existingTicket);
            }
        }

        return ticketRepository.saveAll(updatedTickets);
    }

    public void deleteOneTicket(int id) {
        ticketRepository.deleteById(id);
    }

    public void deleteAllTickets() {
        ticketRepository.deleteAll();
    }
}
