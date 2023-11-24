package com.rflwn.app.service;

import com.rflwn.app.entity.Driver;
import com.rflwn.app.repostiory.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(int id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        (String.format("Водитель с id = %d не найден", id)));
    }

    public List<Driver> addNewDriver(List<Driver> drivers) {
        return driverRepository.saveAll(drivers);
    }

    public Driver updateDriverInfo(Driver driver) {
        return driverRepository.save(driver);
    }

    public void deleteDriverById(int id) {
        Driver driverById = getDriverById(id);
        if (driverById != null) {
            driverRepository.deleteById(id);
        }
    }
}
