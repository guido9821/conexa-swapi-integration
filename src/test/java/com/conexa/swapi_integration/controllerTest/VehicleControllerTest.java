package com.conexa.swapi_integration.controllerTest;


import com.conexa.swapi_integration.controller.VehicleController;
import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@ActiveProfiles("test")
public class VehicleControllerTest {

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private VehicleController vehicleController;


    @Test
    public void getAllVehicleTest() throws Exception {
        ResponseWrapperPaged<VehicleDTO> expectedVehicle = new ResponseWrapperPaged<>();
        when(vehicleService.getAllVehicle(1,5)).thenReturn(expectedVehicle);

        ResponseWrapperPaged<VehicleDTO> actualVehicle = vehicleController.getAllVehicle(1,5);

        assertEquals(expectedVehicle, actualVehicle);

        verify(vehicleService, times(1)).getAllVehicle(1,5);
    }

    @Test
    public void getVehicleByIdTest() throws Exception {
        int id = 1;
        VehicleDTO expectedFilm = new VehicleDTO();
        when(vehicleService.findVehicleById(id)).thenReturn(expectedFilm);

        VehicleDTO actualFilm = vehicleController.findVehicleById(id);

        assertEquals(expectedFilm, actualFilm);

        verify(vehicleService, times(1)).findVehicleById(id);
    }

    @Test
    void getVehiclesByNameTest() {
        String name = "Sand Crawler";
        List<VehicleDTO> expectedVehicle = Arrays.asList(new VehicleDTO(), new VehicleDTO());
        when(vehicleService.findVehiclesByName(name)).thenReturn(expectedVehicle);

        List<VehicleDTO> actualVehicle = vehicleController.findVehiclesByName(name);

        assertEquals(expectedVehicle, actualVehicle);

        verify(vehicleService, times(1)).findVehiclesByName(name);
    }

    @Test
    void getVehiclesByModelTest() {
        String model = "Corellia Mining Corporation";
        List<VehicleDTO> expectedVehicle = Arrays.asList(new VehicleDTO(), new VehicleDTO());
        when(vehicleService.findVehiclesByModel(model)).thenReturn(expectedVehicle);

        List<VehicleDTO> actualVehicle = vehicleController.findVehiclesByModel(model);

        assertEquals(expectedVehicle, actualVehicle);

        verify(vehicleService, times(1)).findVehiclesByModel(model);
    }
}
