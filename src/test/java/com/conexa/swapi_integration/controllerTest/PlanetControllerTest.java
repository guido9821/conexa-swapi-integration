package com.conexa.swapi_integration.controllerTest;


import com.conexa.swapi_integration.controller.PlanetController;
import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

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
    public void getAllFilmsTest() throws Exception {
        ResponseWrapperPaged<PlanetDTO> expectedPlanet = new ResponseWrapperPaged<>();
        when(planetService.getAllPlanets(1,5)).thenReturn(expectedPlanet);

        ResponseWrapperPaged<PlanetDTO> actualPlanet = planetController.getAllPlanets(1,5);

        assertEquals(expectedPlanet, actualPlanet);

        verify(planetService, times(1)).getAllPlanets(1,5);
    }

    @Test
    public void getFilmByIdTest() throws Exception {
        int id = 1;
        PlanetDTO expectedPlanet = new PlanetDTO();
        when(planetService.findPlanetById(id)).thenReturn(expectedPlanet);

        PlanetDTO actualFilm = planetController.findPlanetById(id);

        assertEquals(expectedPlanet, actualFilm);

        verify(planetService, times(1)).findPlanetById(id);
    }

    @Test
    void getFilmByNameTest() {
        String title = "A New Hope";
        List<PlanetDTO> expectedPlanet = Arrays.asList(new PlanetDTO(), new PlanetDTO());
        when(planetService.findPlanetByName(title)).thenReturn(expectedPlanet);

        List<PlanetDTO> actualPlanet = planetController.findPlanetByName(title);

        assertEquals(expectedPlanet, actualPlanet);

        verify(planetService, times(1)).findPlanetByName(title);
    }
    
}
