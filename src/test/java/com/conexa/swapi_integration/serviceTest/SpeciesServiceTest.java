package com.conexa.swapi_integration.serviceTest;


import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.impl.SpeciesServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpeciesServiceTest {
    
    @InjectMocks
    private SpeciesServiceImpl speciesServiceImpl;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllplanetsOkTest() {

        ResponseWrapperPaged<SpeciesDTO> mockResponse = new ResponseWrapperPaged<>();
        mockResponse.setResults(Collections.singletonList(new SpeciesDTO()));

        ResponseEntity<ResponseWrapperPaged<SpeciesDTO>> mockEntity = mock(ResponseEntity.class);
        when(mockEntity.getBody()).thenReturn(mockResponse);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/species/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<SpeciesDTO> result = speciesServiceImpl.getAllSpecies(1, 10);

        assertNotNull(result);
        assertNotNull(result.getResults());
        assertEquals(1, result.getResults().size());
        verify(restTemplate, times(1)).exchange(
                eq("https://www.swapi.tech/api/species/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    public void getAllSpeciessNullTest() {

        ResponseEntity<ResponseWrapperPaged<SpeciesDTO>> mockEntity = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/species/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<SpeciesDTO> result = speciesServiceImpl.getAllSpecies(1, 10);

        assertNull(result);
    }

    @Test
    public void findSpecieByIdOkTest() {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Human\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        SpeciesDTO actualSpecies = speciesServiceImpl.findSpeciesById(1);

        SpeciesDTO expectedPerson = new SpeciesDTO();
        expectedPerson.setName("Human");

        assertEquals(expectedPerson.getName(), actualSpecies.getName());
    }


    @Test
    public void findSpeciesNameIdOkTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Human\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<SpeciesDTO> actualPerson = speciesServiceImpl.findSpeciesByName("Human");

        SpeciesDTO expectedPerson = new SpeciesDTO();
        expectedPerson.setName("Human");

        assertEquals(expectedPerson.getName(), actualPerson.get(0).getName());
    }
}
