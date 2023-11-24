package com.rflwn.app.controller;

import com.rflwn.app.dto.RouteDto;
import com.rflwn.app.entity.Route;
import com.rflwn.app.mapper.ModelDtoMapper;
import com.rflwn.app.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/routes")
public class RouteController {
    private final RouteService routeService;
    private final ModelDtoMapper modelDtoMapper;

    @GetMapping
    public List<RouteDto> getAllRoutesInfo() {
        List<Route> allRoutesInfo = routeService.getRoutesInfo();
        return modelDtoMapper.modelListToDtoList(allRoutesInfo, RouteDto.class);
    }

    @GetMapping("{id}")
    public RouteDto getOneRouteInfo(@PathVariable("id") int id) {
        Route routeById = routeService.getRouteById(id);
        return modelDtoMapper.toDto(routeById, RouteDto.class);
    }

    @PostMapping
    public List<RouteDto> createRoutesInfo(@RequestBody List<RouteDto> routeDtoList) {
        List<Route> routeListModels = modelDtoMapper.dtoListToModelList(routeDtoList, Route.class);
        routeService.createRoutes(routeListModels);
        return modelDtoMapper.modelListToDtoList(routeListModels, RouteDto.class);
    }

    @PutMapping("{id}")
    public RouteDto changeRouteInfo(@PathVariable("id") Route routeFromDb, @RequestBody RouteDto routeDto) {
        BeanUtils.copyProperties(routeDto, routeFromDb, "id");
        routeService.changeRouteInfo(routeFromDb);
        return modelDtoMapper.toDto(routeFromDb, RouteDto.class);
    }

    @DeleteMapping("{id}")
    public void deleteRoute(@PathVariable("id") int id) {
        routeService.deleteRoute(id);
    }

    @DeleteMapping
    public void deleteAllRoutes(){
        routeService.deleteAllRoutes();
    }
}
