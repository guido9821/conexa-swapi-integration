package com.conexa.swapi_integration.controller;


import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles/")
    public ResponseWrapperPaged<VehicleDTO> getAllVehicle(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int limit){
        return vehicleService.getAllVehicle(page, limit);
    }

    @GetMapping("/vehicles/{id}")
    public VehicleDTO findVehicleById(@PathVariable int id) {
        return vehicleService.findVehicleById(id);
    }

    @GetMapping("/vehicles/searchByName/")
    public List<VehicleDTO> findVehiclesByName(@RequestParam String name) {
        return vehicleService.findVehiclesByName(name);
    }

    @GetMapping("/vehicles/searchByModel/")
    public List<VehicleDTO> findVehiclesByModel(@RequestParam String model) {
        return vehicleService.findVehiclesByModel(model);
    }

}
