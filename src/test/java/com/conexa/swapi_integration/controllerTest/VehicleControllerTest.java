package com.conexa.swapi_integration.controllerTest;


import com.conexa.swapi_integration.controller.VehicleController;
import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class VehicleControllerTest {

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private VehicleController vehicleController;


    @Test
    public void getAllVehicleTest() {
        ResponseWrapperPaged<VehicleDTO> expectedVehicle = new ResponseWrapperPaged<>();
        when(vehicleService.getAllVehicle(1,5)).thenReturn(expectedVehicle);

        ResponseEntity<ResponseWrapperPaged<VehicleDTO>> actualVehicle = vehicleController.getAllVehicle(1,5);

        assertEquals(expectedVehicle, actualVehicle.getBody());

        verify(vehicleService, times(1)).getAllVehicle(1,5);
    }

    @Test
    public void getAllVehicleRuntimeExceptionTest() throws Exception {
        when(vehicleService.getAllVehicle(1,5)).thenThrow(RuntimeException.class);

        ResponseEntity<ResponseWrapperPaged<VehicleDTO>> actualVehicle = vehicleController.getAllVehicle(1,5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualVehicle.getStatusCode());
    }

    @Test
    public void getVehicleByIdTest() throws Exception {
        int id = 1;
        VehicleDTO expectedFilm = new VehicleDTO();
        when(vehicleService.findVehicleById(id)).thenReturn(expectedFilm);

        ResponseEntity<VehicleDTO> actualFilm = vehicleController.findVehicleById(id);

        assertEquals(expectedFilm, actualFilm.getBody());

        verify(vehicleService, times(1)).findVehicleById(id);
    }

    @Test
    public void getVehicleByIdRuntimeExceptionTest() throws Exception {
        when(vehicleService.findVehicleById(1)).thenThrow(IOException.class);

        ResponseEntity<VehicleDTO> actualVehicle = vehicleController.findVehicleById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualVehicle.getStatusCode());
    }

    @Test
    public void getVehicleByIdItemNotFoundExceptionTest() throws Exception {
        when(vehicleService.findVehicleById(1)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<VehicleDTO> actualVehicle = vehicleController.findVehicleById(1);

        assertEquals(HttpStatus.NOT_FOUND, actualVehicle.getStatusCode());
    }


    @Test
    void getVehiclesByNameTest() throws IOException {
        String name = "Sand Crawler";
        List<VehicleDTO> expectedVehicle = Arrays.asList(new VehicleDTO(), new VehicleDTO());
        when(vehicleService.findVehiclesByName(name)).thenReturn(expectedVehicle);

        ResponseEntity<List<VehicleDTO>> actualVehicle = vehicleController.findVehiclesByName(name);

        assertEquals(expectedVehicle, actualVehicle.getBody());

        verify(vehicleService, times(1)).findVehiclesByName(name);
    }

    @Test
    public void getVehicleByNameItemNotFoundExceptionTest() throws Exception {
        String name = "Sand Crawler";
        when(vehicleService.findVehiclesByName(name)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<List<VehicleDTO>> actualVehicle = vehicleController.findVehiclesByName(name);

        assertEquals(HttpStatus.NOT_FOUND, actualVehicle.getStatusCode());
    }

    @Test
    public void getVehicleByNameRuntimeExceptionTest() throws Exception {
        String name = "Sand Crawler";
        when(vehicleService.findVehiclesByName(name)).thenThrow(IOException.class);

        ResponseEntity<List<VehicleDTO>> actualVehicle = vehicleController.findVehiclesByName(name);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualVehicle.getStatusCode());
    }

    @Test
    void getVehiclesByModelTest() throws IOException {
        String model = "Corellia Mining Corporation";
        List<VehicleDTO> expectedVehicle = Arrays.asList(new VehicleDTO(), new VehicleDTO());
        when(vehicleService.findVehiclesByModel(model)).thenReturn(expectedVehicle);

        ResponseEntity<List<VehicleDTO>> actualVehicle = vehicleController.findVehiclesByModel(model);

        assertEquals(expectedVehicle, actualVehicle.getBody());

        verify(vehicleService, times(1)).findVehiclesByModel(model);
    }

    @Test
    public void getVehicleByModelItemNotFoundExceptionTest() throws Exception {
        String model = "Corellia Mining Corporation";
        when(vehicleService.findVehiclesByModel(model)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<List<VehicleDTO>> actualVehicle = vehicleController.findVehiclesByModel(model);

        assertEquals(HttpStatus.NOT_FOUND, actualVehicle.getStatusCode());
    }

    @Test
    public void getVehicleByModelRuntimeExceptionTest() throws Exception {
        String model = "Corellia Mining Corporation";
        when(vehicleService.findVehiclesByModel(model)).thenThrow(IOException.class);

        ResponseEntity<List<VehicleDTO>> actualVehicle = vehicleController.findVehiclesByModel(model);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualVehicle.getStatusCode());
    }
}
