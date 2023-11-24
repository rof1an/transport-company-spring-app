package com.rflwn.app.controller;

import com.rflwn.app.dto.RouteListDto;
import com.rflwn.app.entity.RouteList;
import com.rflwn.app.mapper.ModelDtoMapper;
import com.rflwn.app.service.RouteListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/routes-list")
public class RouteListController {
    private final ModelDtoMapper modelDtoMapper;
    private final RouteListService routeListService;

    @GetMapping
    public List<RouteListDto> getAllRoutesListInfo() {
        List<RouteList> allRoutesListInfoModels = routeListService.getAllRoutesListInfo();
        return modelDtoMapper.modelListToDtoList(allRoutesListInfoModels, RouteListDto.class);
    }

    @GetMapping("{id}")
    public RouteListDto getRouteListById(@PathVariable("id") int id){
        RouteListDto routeListById = getRouteListById(id);
        return modelDtoMapper.toDto(routeListById, RouteListDto.class);
    }

    @GetMapping("travel-time/{id}")
    public String getRouteListTravelTime(@PathVariable("id") int id) {
        return routeListService.getRouteListTravelTime(id);
    }

    @GetMapping("seats/{id}")
    public int getAvailableSeats(@PathVariable("id") int id) {
        return routeListService.getAvailableSeats(id);
    }

    @PostMapping
    public List<RouteListDto> addNewRoutesInfoList(@RequestBody List<RouteListDto> routeListDtos) {
        List<RouteList> routeModelLists = modelDtoMapper.dtoListToModelList(routeListDtos, RouteList.class);
        List<RouteList> returnedRouteList = routeListService.addNewRoutesList(routeModelLists);
        return modelDtoMapper.modelListToDtoList(returnedRouteList, RouteListDto.class);
    }

    @PutMapping("{id}")
    public RouteListDto updateRouteListInfo(@PathVariable("id") RouteList existingRouteList,
                                            @RequestBody RouteListDto newRouteList) {
        BeanUtils.copyProperties(newRouteList, existingRouteList, "id");
        RouteList returnedRouteList = routeListService.updateRouteListInformation(existingRouteList);
        return modelDtoMapper.toDto(returnedRouteList, RouteListDto.class);
    }

    @DeleteMapping
    public void deleteAllRoutesList() {
        routeListService.deleteAllRoutesList();
    }
}
