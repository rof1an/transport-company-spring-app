package com.rflwn.app.service;

import com.rflwn.app.dto.RouteListDto;
import com.rflwn.app.entity.Driver;
import com.rflwn.app.entity.Route;
import com.rflwn.app.entity.RouteList;
import com.rflwn.app.entity.Transport;
import com.rflwn.app.repostiory.RouteListRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteListService {
    private final TransportService transportService;
    private final DriverService driverService;
    private final RouteService routeService;
    private final RouteListRepository routeListRepository;

    public List<RouteList> getAllRoutesListInfo() {
        return routeListRepository.findAll();
    }

    public RouteList getRouteListById(int id) {
        return routeListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Информация о маршруте с id = %d не найдена", id)));
    }

    public String getRouteListTravelTime(int id) {
        RouteList routeListById = getRouteListById(id);
        LocalDateTime departureDate = routeListById.getDepartureDate();
        String travelTime = routeService.getRouteById(routeListById.getRoute().getId()).getTravelTime();

        // Convert the travelTime to ISO-8601 duration format - isoDurationString
        String isoDurationString = "PT" + travelTime.replace(":", "H") + "M";
        Duration duration = Duration.parse(isoDurationString);
        LocalDateTime arrivalDate = departureDate.plus(duration);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return "Время отправления: " + departureDate.format(formatter) + ", Время прибытия: " + arrivalDate.format(formatter);
    }

    public int getAvailableSeats(int id){
        RouteList routeListById = getRouteListById(id);
        return routeListById.getRemainingTickets();
    }

    public List<RouteList> addNewRoutesList(List<RouteList> routeLists) {
        routeLists.forEach(route -> {
            Transport transportById = transportService.getTransportInfoById(route.getTransport().getId());
            Driver driverById = driverService.getDriverById(route.getDriver().getId());
            Route routeById = routeService.getRouteById(route.getRoute().getId());

            route.setTransport(transportById);
            route.setDriver(driverById);
            route.setRoute(routeById);
            route.setRemainingTickets(transportById.getCapacity());
        });

        return routeListRepository.saveAll(routeLists);
    }

    public RouteList updateRouteListInformation(RouteList routeList) {
        return routeListRepository.save(routeList);
    }

    public void deleteAllRoutesList() {
        routeListRepository.deleteAll();
    }
}
