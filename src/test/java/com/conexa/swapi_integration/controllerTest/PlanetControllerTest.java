package com.conexa.swapi_integration.controllerTest;


import com.conexa.swapi_integration.controller.PlanetController;
import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PlanetService;
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
public class PlanetControllerTest {


    @MockBean
    private PlanetService planetService;

    @Autowired
    private PlanetController planetController;


    @Test
    public void getAllPlanetsTest() {
        ResponseWrapperPaged<PlanetDTO> expectedPlanet = new ResponseWrapperPaged<>();
        when(planetService.getAllPlanets(1,5)).thenReturn(expectedPlanet);

        ResponseEntity<ResponseWrapperPaged<PlanetDTO>> actualPlanet = planetController.getAllPlanets(1,5);

        assertEquals(expectedPlanet, actualPlanet.getBody());

        verify(planetService, times(1)).getAllPlanets(1,5);
    }

    @Test
    public void getAllPlanetsRuntimeExceptionTest() {
        when(planetService.getAllPlanets(1,5)).thenThrow(RuntimeException.class);

        ResponseEntity<ResponseWrapperPaged<PlanetDTO>> actualPlanet = planetController.getAllPlanets(1,5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualPlanet.getStatusCode());
    }

    @Test
    public void getPlanetByIdTest() throws Exception {
        int id = 1;
        PlanetDTO expectedPlanet = new PlanetDTO();
        when(planetService.findPlanetById(id)).thenReturn(expectedPlanet);

        ResponseEntity<PlanetDTO> actualPlanet = planetController.findPlanetById(id);

        assertEquals(expectedPlanet, actualPlanet.getBody());

        verify(planetService, times(1)).findPlanetById(id);
    }

    @Test
    public void getPlanetByIdRuntimeExceptionTest() throws Exception {
        when(planetService.findPlanetById(1)).thenThrow(IOException.class);

        ResponseEntity<PlanetDTO> actualPlanet = planetController.findPlanetById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualPlanet.getStatusCode());
    }

    @Test
    public void getPlanetByIdItemNotFoundExceptionTest() throws Exception {
        when(planetService.findPlanetById(1)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<PlanetDTO> actualPlanet = planetController.findPlanetById(1);

        assertEquals(HttpStatus.NOT_FOUND, actualPlanet.getStatusCode());
    }

    @Test
    void getPlanetByNameTest() throws IOException {
        String name = "Tatooine";
        List<PlanetDTO> expectedPlanet = Arrays.asList(new PlanetDTO(), new PlanetDTO());
        when(planetService.findPlanetByName(name)).thenReturn(expectedPlanet);

        ResponseEntity<List<PlanetDTO>> actualPlanet = planetController.findPlanetByName(name);

        assertEquals(expectedPlanet, actualPlanet.getBody());

        verify(planetService, times(1)).findPlanetByName(name);
    }

    @Test
    public void getPlanetByNameItemNotFoundExceptionTest() throws Exception {
        String name = "Tatooine";
        when(planetService.findPlanetByName(name)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<List<PlanetDTO>> actualPlanet = planetController.findPlanetByName(name);

        assertEquals(HttpStatus.NOT_FOUND, actualPlanet.getStatusCode());
    }

    @Test
    public void getPlanetByNameRuntimeExceptionTest() throws Exception {
        String name = "Tatooine";
        when(planetService.findPlanetByName(name)).thenThrow(IOException.class);

        ResponseEntity<List<PlanetDTO>> actualPlanet = planetController.findPlanetByName(name);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualPlanet.getStatusCode());
    }
    
}
