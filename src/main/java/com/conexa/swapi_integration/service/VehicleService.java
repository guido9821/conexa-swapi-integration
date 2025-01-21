package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

import java.io.IOException;
import java.util.List;

public interface VehicleService {
    ResponseWrapperPaged<VehicleDTO> getAllVehicle(int page, int limit);
    VehicleDTO findVehicleById(int id) throws IOException;
    List<VehicleDTO> findVehiclesByName(String name) throws IOException;
    List<VehicleDTO> findVehiclesByModel(String model) throws IOException;
}
