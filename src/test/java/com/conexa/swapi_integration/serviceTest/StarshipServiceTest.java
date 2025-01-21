package com.conexa.swapi_integration.serviceTest;


import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.impl.StarshipServiceImpl;
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
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class StarshipServiceTest {

    @InjectMocks
    private StarshipServiceImpl starshipServiceImpl;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllStarshipOkTest() {

        ResponseWrapperPaged<StarshipDTO> mockResponse = new ResponseWrapperPaged<>();
        mockResponse.setResults(Collections.singletonList(new StarshipDTO()));

        ResponseEntity<ResponseWrapperPaged<StarshipDTO>> mockEntity = mock(ResponseEntity.class);
        when(mockEntity.getBody()).thenReturn(mockResponse);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/starships/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<StarshipDTO> result = starshipServiceImpl.getAllStarship(1, 10);

        assertNotNull(result);
        assertNotNull(result.getResults());
        assertEquals(1, result.getResults().size());
        verify(restTemplate, times(1)).exchange(
                eq("https://www.swapi.tech/api/starships/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    public void getAllStarshipsNullTest() {

        ResponseEntity<ResponseWrapperPaged<StarshipDTO>> mockEntity = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/starships/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<StarshipDTO> result = starshipServiceImpl.getAllStarship(1, 10);

        assertNull(result);
    }

    @Test
    public void findSpecieByIdOkTest() {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Death Star\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        StarshipDTO actualStarship = starshipServiceImpl.findStarshipById(1);

        StarshipDTO expectedPerson = new StarshipDTO();
        expectedPerson.setName("Death Star");

        assertEquals(expectedPerson.getName(), actualStarship.getName());
    }

    @Test
    public void findStarshipByIdExceptionTest() {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Death Star\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectFromJson(any(), eq(StarshipDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));
            assertThrows(RuntimeException.class, () -> starshipServiceImpl.findStarshipById(1));
        }
    }


    @Test
    public void findStarshipNameIdOkTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Death Star\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<StarshipDTO> actualPerson = starshipServiceImpl.findStarshipsByName("Death Star");

        StarshipDTO expectedPerson = new StarshipDTO();
        expectedPerson.setName("Death Star");

        assertEquals(expectedPerson.getName(), actualPerson.get(0).getName());
    }

    @Test
    public void findStarshipsByNameExceptionTest()  {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Death Star\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(StarshipDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));
            assertThrows(RuntimeException.class, () -> starshipServiceImpl.findStarshipsByName("Death Star"));
        }
    }

    @Test
    public void findStarshipsByModelOkTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"DS-1 Orbital Battle Station\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<StarshipDTO> actualPerson = starshipServiceImpl.findStarshipsByModel("DS-1 Orbital Battle Station");

        StarshipDTO expectedPerson = new StarshipDTO();
        expectedPerson.setName("DS-1 Orbital Battle Station");

        assertEquals(expectedPerson.getName(), actualPerson.get(0).getName());
    }

    @Test
    public void findStarshipsByModelExceptionTest()  {

        String jsonResponse = "{\"result\":[{\"properties\":{\"model\":\"DS-1 Orbital Battle Station\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(StarshipDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));
            assertThrows(RuntimeException.class, () -> starshipServiceImpl.findStarshipsByModel("DS-1 Orbital Battle Station"));
        }
    }
    
}
