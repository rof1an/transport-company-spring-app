package com.rflwn.app.controller;

import com.rflwn.app.dto.DriverDto;
import com.rflwn.app.entity.Driver;
import com.rflwn.app.mapper.ModelDtoMapper;
import com.rflwn.app.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/drivers")
public class DriverController {
    private final DriverService driverService;
    private final ModelDtoMapper modelDtoMapper;

    @GetMapping
    public List<DriverDto> getDrivers() {
        List<Driver> allDrivers = driverService.getAllDrivers();
        return modelDtoMapper.modelListToDtoList(allDrivers, DriverDto.class);
    }

    @GetMapping("{id}")
    public DriverDto getDriverInfoById(@PathVariable("id") int id) {
        Driver driverById = driverService.getDriverById(id);
        return modelDtoMapper.toDto(driverById, DriverDto.class);
    }

    @GetMapping("/initials/{id}")
    public String getLastNameWithInitials(@PathVariable("id") int id) {
        return driverService.getDriverLastNameWithInitials(id);
    }

    @PostMapping
    public List<DriverDto> addNewDriver(@RequestBody List<DriverDto> driverListDto) {
        List<Driver> mappedDrivers = modelDtoMapper.modelListToDtoList(driverListDto, Driver.class);
        driverService.addNewDriver(mappedDrivers);
        return modelDtoMapper.dtoListToModelList(mappedDrivers, DriverDto.class);
    }

    @PutMapping("{id}")
    public DriverDto updateDriverInfo(@PathVariable("id") Driver driverFromDb, @RequestBody DriverDto driverDto) {
        BeanUtils.copyProperties(driverDto, driverFromDb, "id");
        driverService.updateDriverInfo(driverFromDb);
        return modelDtoMapper.toDto(driverFromDb, DriverDto.class);
    }

    @DeleteMapping("{id}")
    public void deleteDriver(@PathVariable int id) {
        driverService.deleteDriverById(id);
    }

    @DeleteMapping
    public void deleteAllDrivers(){
        driverService.deleteAllDrivers();
    }
}
