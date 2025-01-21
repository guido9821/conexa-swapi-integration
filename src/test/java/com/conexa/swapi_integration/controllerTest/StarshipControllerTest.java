package com.conexa.swapi_integration.controllerTest;


import com.conexa.swapi_integration.controller.StarshipController;
import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.StarshipService;
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
public class StarshipControllerTest {
    
    @MockBean
    private StarshipService starshipService;

    @Autowired
    private StarshipController starshipController;


    @Test
    public void getAllStarshipTest() {
        ResponseWrapperPaged<StarshipDTO> expectedStarship = new ResponseWrapperPaged<>();
        when(starshipService.getAllStarship(1,5)).thenReturn(expectedStarship);

        ResponseEntity<ResponseWrapperPaged<StarshipDTO>> actualStarship = starshipController.getAllStarship(1,5);

        assertEquals(expectedStarship, actualStarship.getBody());

        verify(starshipService, times(1)).getAllStarship(1,5);
    }

    @Test
    public void getAllStarshipRuntimeExceptionTest() {
        when(starshipService.getAllStarship(1,5)).thenThrow(RuntimeException.class);

        ResponseEntity<ResponseWrapperPaged<StarshipDTO>> actualStarship = starshipController.getAllStarship(1,5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualStarship.getStatusCode());
    }

    @Test
    public void getStarshipByIdTest() throws Exception {
        int id = 1;
        StarshipDTO expectedFilm = new StarshipDTO();
        when(starshipService.findStarshipById(id)).thenReturn(expectedFilm);

        ResponseEntity<StarshipDTO> actualFilm = starshipController.findStarshipById(id);

        assertEquals(expectedFilm, actualFilm.getBody());

        verify(starshipService, times(1)).findStarshipById(id);
    }
    
    @Test
    public void getStarshipByIdRuntimeExceptionTest() throws Exception {
        when(starshipService.findStarshipById(1)).thenThrow(IOException.class);

        ResponseEntity<StarshipDTO> actualStarship = starshipController.findStarshipById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualStarship.getStatusCode());
    }

    @Test
    public void getStarshipByIdItemNotFoundExceptionTest() throws Exception {
        when(starshipService.findStarshipById(1)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<StarshipDTO> actualStarship = starshipController.findStarshipById(1);

        assertEquals(HttpStatus.NOT_FOUND, actualStarship.getStatusCode());
    }

    @Test
    void getStarshipsByNameTest() throws IOException {
        String name = "Death Star";
        List<StarshipDTO> expectedStarship = Arrays.asList(new StarshipDTO(), new StarshipDTO());
        when(starshipService.findStarshipsByName(name)).thenReturn(expectedStarship);

        ResponseEntity<List<StarshipDTO>> actualStarship = starshipController.findStarshipsByName(name);

        assertEquals(expectedStarship, actualStarship.getBody());

        verify(starshipService, times(1)).findStarshipsByName(name);
    }

    @Test
    public void getStarshipByNameItemNotFoundExceptionTest() throws Exception {
        String name = "Human";
        when(starshipService.findStarshipsByName(name)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<List<StarshipDTO>> actualStarship = starshipController.findStarshipsByName(name);

        assertEquals(HttpStatus.NOT_FOUND, actualStarship.getStatusCode());
    }

    @Test
    public void getStarshipByNameRuntimeExceptionTest() throws Exception {
        String name = "Human";
        when(starshipService.findStarshipsByName(name)).thenThrow(IOException.class);

        ResponseEntity<List<StarshipDTO>> actualStarship = starshipController.findStarshipsByName(name);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualStarship.getStatusCode());
    }
    
    @Test
    void getStarshipsByModelTest() throws IOException {
        String model = "DS-1 Orbital Battle Station";
        List<StarshipDTO> expectedStarship = Arrays.asList(new StarshipDTO(), new StarshipDTO());
        when(starshipService.findStarshipsByModel(model)).thenReturn(expectedStarship);

        ResponseEntity<List<StarshipDTO>> actualStarship = starshipController.findStarshipsByModel(model);

        assertEquals(expectedStarship, actualStarship.getBody());

        verify(starshipService, times(1)).findStarshipsByModel(model);
    }

    @Test
    public void getStarshipByModelItemNotFoundExceptionTest() throws Exception {
        String model = "DS-1 Orbital Battle Station";
        when(starshipService.findStarshipsByModel(model)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<List<StarshipDTO>> actualStarship = starshipController.findStarshipsByModel(model);

        assertEquals(HttpStatus.NOT_FOUND, actualStarship.getStatusCode());
    }

    @Test
    public void getStarshipByModelRuntimeExceptionTest() throws Exception {
        String model = "DS-1 Orbital Battle Station";
        when(starshipService.findStarshipsByModel(model)).thenThrow(IOException.class);

        ResponseEntity<List<StarshipDTO>> actualStarship = starshipController.findStarshipsByModel(model);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualStarship.getStatusCode());
    }

}
