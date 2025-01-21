package com.conexa.swapi_integration.serviceTest;

import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.impl.VehicleServiceImpl;
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

public class VehicleServiceTest {

    @InjectMocks
    private VehicleServiceImpl vehicleServiceImpl;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllVehicleOkTest() {

        ResponseWrapperPaged<VehicleDTO> mockResponse = new ResponseWrapperPaged<>();
        mockResponse.setResults(Collections.singletonList(new VehicleDTO()));

        ResponseEntity<ResponseWrapperPaged<VehicleDTO>> mockEntity = mock(ResponseEntity.class);
        when(mockEntity.getBody()).thenReturn(mockResponse);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/vehicles/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<VehicleDTO> result = vehicleServiceImpl.getAllVehicle(1, 10);

        assertNotNull(result);
        assertNotNull(result.getResults());
        assertEquals(1, result.getResults().size());
        verify(restTemplate, times(1)).exchange(
                eq("https://www.swapi.tech/api/vehicles/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    public void getAllVehiclesNullTest() {

        ResponseEntity<ResponseWrapperPaged<VehicleDTO>> mockEntity = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/vehicles/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<VehicleDTO> result = vehicleServiceImpl.getAllVehicle(1, 10);

        assertNull(result);
    }

    @Test
    public void findSpecieByIdOkTest() {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Sand Crawler\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        VehicleDTO actualVehicle = vehicleServiceImpl.findVehicleById(1);

        VehicleDTO expectedPerson = new VehicleDTO();
        expectedPerson.setName("Sand Crawler");

        assertEquals(expectedPerson.getName(), actualVehicle.getName());
    }

    @Test
    public void findVehicleByIdExceptionTest() {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Sand Crawler\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectFromJson(any(), eq(VehicleDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));
            assertThrows(RuntimeException.class, () -> vehicleServiceImpl.findVehicleById(1));
        }
    }


    @Test
    public void findVehiclesNameIdOkTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Sand Crawler\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<VehicleDTO> actualPerson = vehicleServiceImpl.findVehiclesByName("Sand Crawler");

        VehicleDTO expectedPerson = new VehicleDTO();
        expectedPerson.setName("Sand Crawler");

        assertEquals(expectedPerson.getName(), actualPerson.get(0).getName());
    }

    @Test
    public void findVehiclesByNameExceptionTest()  {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Sand Crawler\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(VehicleDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));
            assertThrows(RuntimeException.class, () -> vehicleServiceImpl.findVehiclesByName("and Crawler"));
        }
    }


    @Test
    public void findVehicleModelIdOkTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"model\":\"Digger Crawler\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<VehicleDTO> actualPerson = vehicleServiceImpl.findVehiclesByModel("Digger Crawler");

        VehicleDTO expectedPerson = new VehicleDTO();
        expectedPerson.setName("Digger Crawler");

        assertEquals(expectedPerson.getModel(), actualPerson.get(0).getName());
    }

    @Test
    public void findVehiclesByModelExceptionTest()  {

        String jsonResponse = "{\"result\":[{\"properties\":{\"model\":\"Digger Crawler\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(VehicleDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));
            assertThrows(RuntimeException.class, () -> vehicleServiceImpl.findVehiclesByModel("Digger Crawler"));
        }
    }
}
