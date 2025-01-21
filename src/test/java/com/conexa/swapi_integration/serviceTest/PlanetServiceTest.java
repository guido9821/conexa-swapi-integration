package com.conexa.swapi_integration.serviceTest;

import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.impl.PlanetServiceImpl;
import com.conexa.swapi_integration.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    public void getAllPlanetsOkTest() {

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
    public void findPlanetByIdOkTest() throws IOException {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Tatooine\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        PlanetDTO actualPlanet = planetServiceImpl.findPlanetById(1);

        PlanetDTO expectedPerson = new PlanetDTO();
        expectedPerson.setName("Tatooine");

        assertEquals(expectedPerson.getName(), actualPlanet.getName());
    }

    @Test
    public void findPlanetByIdItemNotFoundExceptionTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Tatooine\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectFromJson(any(), eq(PlanetDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

            assertThrows(ItemNotFoundException.class, () -> planetServiceImpl.findPlanetById(1));
        }
    }

    @Test
    public void findPlanetByIdRuntimeExceptionTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Tatooine\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectFromJson(any(), eq(PlanetDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            assertThrows(RuntimeException.class, () -> planetServiceImpl.findPlanetById(1));
        }
    }


    @Test
    public void findPlanetNameIdOkTest() throws IOException {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Tatooine\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<PlanetDTO> actualPerson = planetServiceImpl.findPlanetByName("Tatooine");

        PlanetDTO expectedPerson = new PlanetDTO();
        expectedPerson.setName("Tatooine");

        assertEquals(expectedPerson.getName(), actualPerson.get(0).getName());
    }

    @Test
    public void findPlanetByNameIdItemNotFoundExceptionTest() {
        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Luke Skywalker\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(PlanetDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

            assertThrows(ItemNotFoundException.class, () -> planetServiceImpl.findPlanetByName("Luke Skywalker"));
        }

    }

    @Test
    public void findPlanetByNameIdRuntimeExceptionTest() {
        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Luke Skywalker\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(PlanetDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            assertThrows(RuntimeException.class, () -> planetServiceImpl.findPlanetByName("Luke Skywalker"));
        }
    }
}
