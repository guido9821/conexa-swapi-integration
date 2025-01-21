package com.conexa.swapi_integration.serviceTest;


import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.service.impl.FilmServiceImpl;
import com.conexa.swapi_integration.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class FilmServiceTest {

    @InjectMocks
    private FilmServiceImpl filmServiceImpl;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllFilmOkTest() {

        String mockResponse = "{\"message\":\"ok\",\"result\":[{\"properties\":{\"characters\":[],\"planets\":[],\"starships\":[],\"vehicles\":[],\"species\":[],\"title\":\"Revenge of the Sith\"}}]}";
        ResponseEntity<String> mockEntity = mock(ResponseEntity.class);
        when(mockEntity.getBody()).thenReturn(mockResponse);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/films/"),
                eq(HttpMethod.GET),
                isNull(),
                eq(String.class)
        )).thenReturn(mockEntity);

        List<FilmDTO> result = filmServiceImpl.getAllFilms();

        assertNotNull(result);
        assertNotNull(result.get(0));
        assertEquals(1, result.size());
        verify(restTemplate, times(1)).exchange(
                eq("https://www.swapi.tech/api/films/"),
                eq(HttpMethod.GET),
                isNull(),
                eq(String.class)
        );
    }

    @Test
    public void getAllFilmsNullTest() {

        String mockResponse = "{\"message\":\"ok\",\"result\":[]}";
        ResponseEntity<String> mockEntity = mock(ResponseEntity.class);
        when(mockEntity.getBody()).thenReturn(mockResponse);

        when(restTemplate.exchange(
                eq("https://www.swapi.tech/api/films/"),
                eq(HttpMethod.GET),
                isNull(),
                eq(String.class)
        )).thenReturn(mockEntity);

        List<FilmDTO> result = filmServiceImpl.getAllFilms();
        assertEquals(result, new ArrayList<>());
        verify(restTemplate, times(1)).exchange(
                eq("https://www.swapi.tech/api/films/"),
                eq(HttpMethod.GET),
                isNull(),
                eq(String.class)
        );
    }

    @Test
    public void findFilmExceptionTest() {

        String jsonResponse = "{\"result\":{\"properties\":{\"title\":\"A New Hope\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(FilmDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));
            assertThrows(RuntimeException.class, () -> filmServiceImpl.getAllFilms());
        }
    }


    @Test
    public void findFilmByIdOkTest() {

        String jsonResponse = "{\"result\":{\"properties\":{\"title\":\"A New Hope\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        FilmDTO actualFilm = filmServiceImpl.findFilmById(1);

        FilmDTO expectedFilm = new FilmDTO();
        expectedFilm.setTitle("A New Hope");

        assertEquals(expectedFilm.getTitle(), actualFilm.getTitle());
    }

    @Test
    public void findFilmByIdExceptionTest() {

        String jsonResponse = "{\"result\":{\"properties\":{\"title\":\"A New Hope\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectFromJson(any(), eq(FilmDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));
            assertThrows(RuntimeException.class, () -> filmServiceImpl.findFilmById(1));
        }
    }


    @Test
    public void findFilmTitleIdOkTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"title\":\"A New Hope\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<FilmDTO> actualPerson = filmServiceImpl.getFilmsByTitle("A New Hope");

        FilmDTO expectedFilm = new FilmDTO();
        expectedFilm.setTitle("A New Hope");

        assertEquals(expectedFilm.getTitle(), actualPerson.get(0).getTitle());
    }

    @Test
    public void findFilmModelByIdOkTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"title\":\"A New Hope\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(Class.class))).thenReturn(responseEntityRaw);

        List<FilmDTO> actualPerson = filmServiceImpl.getFilmsByTitle("A New Hope");

        FilmDTO expectedFilm = new FilmDTO();
        expectedFilm.setTitle("A New Hope");

        assertEquals(expectedFilm.getTitle(), actualPerson.get(0).getTitle());
    }

    @Test
    public void findFilmByNameExceptionTest() {

        String jsonResponse = "{\"result\":[{\"properties\":{\"title\":\"A New Hope\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class))).thenReturn(responseEntityRaw);

        try (MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class)) {
            mockedStatic.when(() -> MapperUtil.getObjectListFromJson(any(), eq(FilmDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));
            assertThrows(RuntimeException.class, () -> filmServiceImpl.getFilmsByTitle("A New Hope"));
        }
    }
}
