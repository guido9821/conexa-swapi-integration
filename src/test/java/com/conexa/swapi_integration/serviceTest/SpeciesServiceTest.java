package com.conexa.swapi_integration.serviceTest;


import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.impl.SpeciesServiceImpl;
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
    public void getAllspeciessOkTest() {

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
    public void getAllSpeciesNullTest() {

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
    public void findSpecieByIdOkTest() throws IOException {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Human\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        SpeciesDTO actualSpecies = speciesServiceImpl.findSpeciesById(1);

        SpeciesDTO expectedPerson = new SpeciesDTO();
        expectedPerson.setName("Human");

        assertEquals(expectedPerson.getName(), actualSpecies.getName());
    }

    @Test
    public void findSpeciesByIdItemNotFoundExceptionTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Tatooine\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectFromJson(any(), eq(SpeciesDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

            assertThrows(ItemNotFoundException.class, () -> speciesServiceImpl.findSpeciesById(1));
        }
    }

    @Test
    public void findSpeciesByIdRuntimeExceptionTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Tatooine\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectFromJson(any(), eq(SpeciesDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            assertThrows(RuntimeException.class, () -> speciesServiceImpl.findSpeciesById(1));
        }
    }


    @Test
    public void findSpeciesNameOkTest() throws IOException {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Human\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<SpeciesDTO> actualPerson = speciesServiceImpl.findSpeciesByName("Human");

        SpeciesDTO expectedPerson = new SpeciesDTO();
        expectedPerson.setName("Human");

        assertEquals(expectedPerson.getName(), actualPerson.get(0).getName());
    }


    @Test
    public void findSpeciesByNameItemNotFoundExceptionTest() {
        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Human\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(SpeciesDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

            assertThrows(ItemNotFoundException.class, () -> speciesServiceImpl.findSpeciesByName("Human"));
        }

    }

    @Test
    public void findSpeciesByNameRuntimeExceptionTest() {
        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Human\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(SpeciesDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            assertThrows(RuntimeException.class, () -> speciesServiceImpl.findSpeciesByName("Human"));
        }
    }
    
}
