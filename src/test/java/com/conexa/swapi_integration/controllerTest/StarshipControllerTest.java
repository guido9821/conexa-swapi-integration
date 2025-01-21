package com.conexa.swapi_integration.controllerTest;


import com.conexa.swapi_integration.controller.StarshipController;
import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.StarshipService;
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
public class StarshipControllerTest {
    
    @MockBean
    private StarshipService starshipService;

    @Autowired
    private StarshipController starshipController;


    @Test
    public void getAllStarshipTest() throws Exception {
        ResponseWrapperPaged<StarshipDTO> expectedStarship = new ResponseWrapperPaged<>();
        when(starshipService.getAllStarship(1,5)).thenReturn(expectedStarship);

        ResponseWrapperPaged<StarshipDTO> actualStarship = starshipController.getAllStarship(1,5);

        assertEquals(expectedStarship, actualStarship);

        verify(starshipService, times(1)).getAllStarship(1,5);
    }

    @Test
    public void getStarshipByIdTest() throws Exception {
        int id = 1;
        StarshipDTO expectedFilm = new StarshipDTO();
        when(starshipService.findStarshipById(id)).thenReturn(expectedFilm);

        StarshipDTO actualFilm = starshipController.findStarshipById(id);

        assertEquals(expectedFilm, actualFilm);

        verify(starshipService, times(1)).findStarshipById(id);
    }

    @Test
    void getStarshipsByNameTest() {
        String name = "Death Star";
        List<StarshipDTO> expectedStarship = Arrays.asList(new StarshipDTO(), new StarshipDTO());
        when(starshipService.findStarshipsByName(name)).thenReturn(expectedStarship);

        List<StarshipDTO> actualStarship = starshipController.findStarshipsByName(name);

        assertEquals(expectedStarship, actualStarship);

        verify(starshipService, times(1)).findStarshipsByName(name);
    }

    @Test
    void getStarshipsByModelTest() {
        String model = "DS-1 Orbital Battle Station";
        List<StarshipDTO> expectedStarship = Arrays.asList(new StarshipDTO(), new StarshipDTO());
        when(starshipService.findStarshipsByModel(model)).thenReturn(expectedStarship);

        List<StarshipDTO> actualStarship = starshipController.findStarshipsByModel(model);

        assertEquals(expectedStarship, actualStarship);

        verify(starshipService, times(1)).findStarshipsByModel(model);
    }

}
