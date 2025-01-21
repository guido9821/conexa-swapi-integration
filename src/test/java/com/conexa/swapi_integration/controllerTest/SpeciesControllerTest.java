package com.conexa.swapi_integration.controllerTest;

import com.conexa.swapi_integration.controller.SpeciesController;
import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.SpeciesService;
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
public class SpeciesControllerTest {

    @MockBean
    private SpeciesService speciesService;

    @Autowired
    private SpeciesController speciesController;


    @Test
    public void getAllSpeciesTest() throws Exception {
        ResponseWrapperPaged<SpeciesDTO> expectedFilms = new ResponseWrapperPaged<>();
        when(speciesService.getAllSpecies(1,5)).thenReturn(expectedFilms);

        ResponseWrapperPaged<SpeciesDTO> actualFilms = speciesController.getAllSpecies(1,5);

        assertEquals(expectedFilms, actualFilms);

        verify(speciesService, times(1)).getAllSpecies(1,5);
    }

    @Test
    public void findSpeciesByIdTest() throws Exception {
        int id = 1;
        SpeciesDTO expectedFilm = new SpeciesDTO();
        when(speciesService.findSpeciesById(id)).thenReturn(expectedFilm);

        SpeciesDTO actualFilm = speciesController.findSpeciesById(id);

        assertEquals(expectedFilm, actualFilm);

        verify(speciesService, times(1)).findSpeciesById(id);
    }

    @Test
    void findSpeciesByNameTest() {
        String name = "Human";
        List<SpeciesDTO> expectedFilms = Arrays.asList(new SpeciesDTO(), new SpeciesDTO());
        when(speciesService.findSpeciesByName(name)).thenReturn(expectedFilms);

        List<SpeciesDTO> actualFilms = speciesController.findSpeciesByName(name);

        assertEquals(expectedFilms, actualFilms);

        verify(speciesService, times(1)).findSpeciesByName(name);
    }
    
}
