package com.rflwn.app.service;

import com.rflwn.app.entity.Route;
import com.rflwn.app.repostiory.RouteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;

    public List<Route> getRoutesInfo() {
        return routeRepository.findAll();
    }

    public Route getRouteById(int id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Маршрут с id = %s не найден", id)));
    }

    public List<Route> createRoutes(List<Route> routesList) {
        return routeRepository.saveAll(routesList);
    }

    public Route changeRouteInfo(Route route) {
        return routeRepository.save(route);
    }

    public void deleteRoute(int id) {
        Route routeById = getRouteById(id);
        if (routeById != null) {
            routeRepository.deleteById(id);
        }
    }

    public void deleteAllRoutes(){
        routeRepository.deleteAll();
    }
}
