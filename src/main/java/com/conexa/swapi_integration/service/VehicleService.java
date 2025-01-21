package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

import java.util.List;

public interface VehicleService {
    ResponseWrapperPaged<VehicleDTO> getAllVehicle(int page, int limit);
    VehicleDTO findVehicleById(int id);
    List<VehicleDTO> findVehiclesByName(String name);
    List<VehicleDTO> findVehiclesByModel(String model);
}
