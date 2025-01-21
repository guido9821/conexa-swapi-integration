package com.conexa.swapi_integration.serviceTest;

import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.impl.PlanetServiceImpl;
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
public class PlanetServiceTest {

    @InjectMocks
    private PlanetServiceImpl planetServiceImpl;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllplanetsOkTest() {

        ResponseWrapperPaged<PlanetDTO> mockResponse = new ResponseWrapperPaged<>();
        mockResponse.setResults(Collections.singletonList(new PlanetDTO()));

        ResponseEntity<ResponseWrapperPaged<PlanetDTO>> mockEntity = mock(ResponseEntity.class);
        when(mockEntity.getBody()).thenReturn(mockResponse);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/planets/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<PlanetDTO> result = planetServiceImpl.getAllPlanets(1, 10);

        assertNotNull(result);
        assertNotNull(result.getResults());
        assertEquals(1, result.getResults().size());
        verify(restTemplate, times(1)).exchange(
                eq("https://www.swapi.tech/api/planets/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    public void getAllPlanetsNullTest() {

        ResponseEntity<ResponseWrapperPaged<PlanetDTO>> mockEntity = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/planets/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<PlanetDTO> result = planetServiceImpl.getAllPlanets(1, 10);

        assertNull(result);
    }

    @Test
    public void findPlanetByIdOkTest() {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Tatooine\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        PlanetDTO actualPlanet = planetServiceImpl.findPlanetById(1);

        PlanetDTO expectedPerson = new PlanetDTO();
        expectedPerson.setName("Tatooine");

        assertEquals(expectedPerson.getName(), actualPlanet.getName());
    }


    @Test
    public void findPlanetNameIdOkTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Tatooine\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<PlanetDTO> actualPerson = planetServiceImpl.findPlanetByName("Tatooine");

        PlanetDTO expectedPerson = new PlanetDTO();
        expectedPerson.setName("Tatooine");

        assertEquals(expectedPerson.getName(), actualPerson.get(0).getName());
    }
}
