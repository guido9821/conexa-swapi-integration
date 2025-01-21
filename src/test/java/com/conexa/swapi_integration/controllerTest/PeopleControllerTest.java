package com.conexa.swapi_integration.controllerTest;

import com.conexa.swapi_integration.controller.PeopleController;
import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PeopleService;
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
public class PeopleControllerTest {

    @MockBean
    private PeopleService peopleService;

    @Autowired
    private PeopleController peopleController;

    @Test
    public void getAllPeoplesTest() throws Exception {
        ResponseWrapperPaged<PeopleDTO> expectedPeople = new ResponseWrapperPaged<>();
        when(peopleService.getAllPeople(1,5)).thenReturn(expectedPeople);

        ResponseEntity<ResponseWrapperPaged<PeopleDTO>> actualPeople = peopleController.getAllPeople(1,5);

        assertEquals(expectedPeople, actualPeople.getBody());

        verify(peopleService, times(1)).getAllPeople(1,5);
    }

    @Test
    public void getAllPeoplesRuntimeExceptionTest() throws Exception {
        when(peopleService.getAllPeople(1,5)).thenThrow(RuntimeException.class);

        ResponseEntity<ResponseWrapperPaged<PeopleDTO>> actualPeople = peopleController.getAllPeople(1,5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualPeople.getStatusCode());
    }

    @Test
    public void getPeopleByIdTest() throws Exception {
        int id = 1;
        PeopleDTO expectedPeople = new PeopleDTO();
        when(peopleService.findPeopleById(id)).thenReturn(expectedPeople);

        ResponseEntity<PeopleDTO> actualPeople = peopleController.getPersonById(id);

        assertEquals(expectedPeople, actualPeople.getBody());

        verify(peopleService, times(1)).findPeopleById(id);
    }

    @Test
    public void getPeopleByIdRuntimeExceptionTest() throws Exception {
        when(peopleService.findPeopleById(1)).thenThrow(IOException.class);

        ResponseEntity<PeopleDTO> actualPeople = peopleController.getPersonById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualPeople.getStatusCode());
    }

    @Test
    public void getPeopleByIdItemNotFoundExceptionTest() throws Exception {
        when(peopleService.findPeopleById(1)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<PeopleDTO> actualPeople = peopleController.getPersonById(1);

        assertEquals(HttpStatus.NOT_FOUND, actualPeople.getStatusCode());
    }

    @Test
    void getPeopleByNameTest() throws IOException {
        String name = "Luke Skywalker";
        List<PeopleDTO> expectedPeoples = Arrays.asList(new PeopleDTO(), new PeopleDTO());
        when(peopleService.findPeopleByName(name)).thenReturn(expectedPeoples);

        ResponseEntity<List<PeopleDTO>> actualPeople = peopleController.getPeopleByName(name);

        assertEquals(expectedPeoples, actualPeople.getBody());

        verify(peopleService, times(1)).findPeopleByName(name);
    }

    @Test
    public void getPeopleByNameItemNotFoundExceptionTest() throws Exception {
        String name = "Luke Skywalker";
        when(peopleService.findPeopleByName(name)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<List<PeopleDTO>> actualPeople = peopleController.getPeopleByName(name);

        assertEquals(HttpStatus.NOT_FOUND, actualPeople.getStatusCode());
    }

    @Test
    public void getPeopleByNameRuntimeExceptionTest() throws Exception {
        String name = "Luke Skywalker";
        when(peopleService.findPeopleByName(name)).thenThrow(IOException.class);

        ResponseEntity<List<PeopleDTO>> actualPeople = peopleController.getPeopleByName(name);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualPeople.getStatusCode());
    }
}
