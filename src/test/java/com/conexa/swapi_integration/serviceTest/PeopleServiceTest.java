package com.conexa.swapi_integration.serviceTest;

import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.impl.PeopleServiceImpl;
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


public class PeopleServiceTest {

    @InjectMocks
    private PeopleServiceImpl peopleServiceImpl;

    @Mock
    RestTemplate restTemplate;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllPeopleOkTest() {

        ResponseWrapperPaged<PeopleDTO> mockResponse = new ResponseWrapperPaged<>();
        mockResponse.setResults(Collections.singletonList(new PeopleDTO()));

        ResponseEntity<ResponseWrapperPaged<PeopleDTO>> mockEntity = mock(ResponseEntity.class);
        when(mockEntity.getBody()).thenReturn(mockResponse);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/people/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<PeopleDTO> result = peopleServiceImpl.getAllPeople(1, 10);

        assertNotNull(result);
        assertNotNull(result.getResults());
        assertEquals(1, result.getResults().size());
        verify(restTemplate, times(1)).exchange(
                eq("https://www.swapi.tech/api/people/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    public void getAllPeopleNullTest() {

        ResponseEntity<ResponseWrapperPaged<PeopleDTO>> mockEntity = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/people/?page=1&limit=10"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockEntity);

        ResponseWrapperPaged<PeopleDTO> result = peopleServiceImpl.getAllPeople(1, 10);

        assertNull(result);
    }

    @Test
    public void findPeopleByIdOkTest() throws IOException {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Luke Skywalker\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        PeopleDTO actualPerson = peopleServiceImpl.findPeopleById(1);

        PeopleDTO expectedPerson = new PeopleDTO();
        expectedPerson.setName("Luke Skywalker");

        assertEquals(expectedPerson.getName(), actualPerson.getName());
    }

    @Test
    public void findPeopleByIdRuntimeExceptionTest() throws IOException {
        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Luke Skywalker\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectFromJson(any(), eq(PeopleDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            assertThrows(RuntimeException.class, () -> peopleServiceImpl.findPeopleById(1));
        }
    }

    @Test
    public void findPeopleByIdItemNotFoundExceptionTest() throws IOException {
        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Luke Skywalker\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectFromJson(any(), eq(PeopleDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

            assertThrows(ItemNotFoundException.class, () -> peopleServiceImpl.findPeopleById(1));
        }
    }

    @Test
    public void findPeopleNameIdOkTest() throws IOException {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Luke Skywalker\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<PeopleDTO> actualPerson = peopleServiceImpl.findPeopleByName("Luke Skywalker");

        PeopleDTO expectedPerson = new PeopleDTO();
        expectedPerson.setName("Luke Skywalker");

        assertEquals(expectedPerson.getName(), actualPerson.get(0).getName());
    }

    @Test
    public void findPeopleByNameIdRuntimeExceptionTest()  {
        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Luke Skywalker\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(PeopleDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            assertThrows(RuntimeException.class, () -> peopleServiceImpl.findPeopleByName("Luke Skywalker"));
        }
    }

    @Test
    public void findPeopleByNameIdItemNotFoundExceptionTest()  {
        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Luke Skywalker\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(PeopleDTO.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

            assertThrows(ItemNotFoundException.class, () -> peopleServiceImpl.findPeopleByName("Luke Skywalker"));
        }
    }
}


