package com.conexa.swapi_integration.controllerTest;

import com.conexa.swapi_integration.controller.SpeciesController;
import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.SpeciesService;
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
public class SpeciesControllerTest {

    @MockBean
    private SpeciesService speciesService;

    @Autowired
    private SpeciesController speciesController;


    @Test
    public void getAllSpeciesTest() throws Exception {
        ResponseWrapperPaged<SpeciesDTO> expectedFilms = new ResponseWrapperPaged<>();
        when(speciesService.getAllSpecies(1,5)).thenReturn(expectedFilms);

        ResponseEntity<ResponseWrapperPaged<SpeciesDTO>> actualFilms = speciesController.getAllSpecies(1,5);

        assertEquals(expectedFilms, actualFilms.getBody());

        verify(speciesService, times(1)).getAllSpecies(1,5);
    }

    @Test
    public void getAllSpeciesRuntimeExceptionTest() throws Exception {
        when(speciesService.getAllSpecies(1,5)).thenThrow(RuntimeException.class);

        ResponseEntity<ResponseWrapperPaged<SpeciesDTO>> actualSpecies = speciesController.getAllSpecies(1,5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualSpecies.getStatusCode());
    }

    @Test
    public void findSpeciesByIdTest() throws Exception {
        int id = 1;
        SpeciesDTO expectedFilm = new SpeciesDTO();
        when(speciesService.findSpeciesById(id)).thenReturn(expectedFilm);

        ResponseEntity<SpeciesDTO> actualFilm = speciesController.findSpeciesById(id);

        assertEquals(expectedFilm, actualFilm.getBody());

        verify(speciesService, times(1)).findSpeciesById(id);
    }

    @Test
    public void getSpeciesByIdRuntimeExceptionTest() throws Exception {
        when(speciesService.findSpeciesById(1)).thenThrow(IOException.class);

        ResponseEntity<SpeciesDTO> actualSpecies = speciesController.findSpeciesById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualSpecies.getStatusCode());
    }

    @Test
    public void getSpeciesByIdItemNotFoundExceptionTest() throws Exception {
        when(speciesService.findSpeciesById(1)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<SpeciesDTO> actualSpecies = speciesController.findSpeciesById(1);

        assertEquals(HttpStatus.NOT_FOUND, actualSpecies.getStatusCode());
    }

    @Test
    void findSpeciesByNameTest() throws IOException {
        String name = "Human";
        List<SpeciesDTO> expectedFilms = Arrays.asList(new SpeciesDTO(), new SpeciesDTO());
        when(speciesService.findSpeciesByName(name)).thenReturn(expectedFilms);

        ResponseEntity<List<SpeciesDTO>> actualFilms = speciesController.findSpeciesByName(name);

        assertEquals(expectedFilms, actualFilms.getBody());

        verify(speciesService, times(1)).findSpeciesByName(name);
    }

    @Test
    public void getSpeciesByNameItemNotFoundExceptionTest() throws Exception {
        String name = "Human";
        when(speciesService.findSpeciesByName(name)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<List<SpeciesDTO>> actualSpecies = speciesController.findSpeciesByName(name);

        assertEquals(HttpStatus.NOT_FOUND, actualSpecies.getStatusCode());
    }

    @Test
    public void getSpeciesByNameRuntimeExceptionTest() throws Exception {
        String name = "Human";
        when(speciesService.findSpeciesByName(name)).thenThrow(IOException.class);

        ResponseEntity<List<SpeciesDTO>> actualSpecies = speciesController.findSpeciesByName(name);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualSpecies.getStatusCode());
    }
    
}
