package com.conexa.swapi_integration.controllerTest;

import com.conexa.swapi_integration.controller.PeopleController;
import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PeopleService;
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
public class PeopleControllerTest {

    @MockBean
    private PeopleService peopleService;

    @Autowired
    private PeopleController peopleController;

    @Test
    public void getAllPeoplesTest() throws Exception {
        ResponseWrapperPaged<PeopleDTO> expectedPeople = new ResponseWrapperPaged<>();
        when(peopleService.getAllPeople(1,5)).thenReturn(expectedPeople);

        ResponseWrapperPaged<PeopleDTO> actualPeople = peopleController.getAllPeople(1,5);

        assertEquals(expectedPeople, actualPeople);

        verify(peopleService, times(1)).getAllPeople(1,5);
    }

    @Test
    public void getPeopleByIdTest() throws Exception {
        int id = 1;
        PeopleDTO expectedPeople = new PeopleDTO();
        when(peopleService.findPeopleById(id)).thenReturn(expectedPeople);

        PeopleDTO actualPeople = peopleController.getPersonById(id);

        assertEquals(expectedPeople, actualPeople);

        verify(peopleService, times(1)).findPeopleById(id);
    }

    @Test
    void getPeopleByNameTest() {
        String name = "Luke Skywalker";
        List<PeopleDTO> expectedPeoples = Arrays.asList(new PeopleDTO(), new PeopleDTO());
        when(peopleService.findPeopleByName(name)).thenReturn(expectedPeoples);

        List<PeopleDTO> actualPeople = peopleController.getPeopleByName(name);

        assertEquals(expectedPeoples, actualPeople);

        verify(peopleService, times(1)).findPeopleByName(name);
    }
}
