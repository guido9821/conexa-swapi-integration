package com.conexa.swapi_integration.controllerTest;

import com.conexa.swapi_integration.controller.FilmController;
import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.service.FilmService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
public class FilmControllerTest {


    @MockBean
    private FilmService filmService;

    @Autowired
    private FilmController filmController;


    @Test
    public void getAllFilmsTest() throws Exception {
        List<FilmDTO> expectedFilms = Arrays.asList(new FilmDTO(), new FilmDTO());
        when(filmService.getAllFilms()).thenReturn(expectedFilms);

        List<FilmDTO> actualFilms = filmController.getAllFilms();

        assertEquals(expectedFilms, actualFilms);

        verify(filmService, times(1)).getAllFilms();
    }

    @Test
    public void getFilmByIdTest() throws Exception {
        int id = 1;
        FilmDTO expectedFilm = new FilmDTO();
        when(filmService.findFilmById(id)).thenReturn(expectedFilm);

        ResponseEntity<FilmDTO> actualFilm = filmController.getFilmById(id);

        assertEquals(expectedFilm, actualFilm.getBody());

        verify(filmService, times(1)).findFilmById(id);
    }

    @Test
    public void getFilmByIdItemNotFoundExceptionTest() throws Exception {
        int id = 1;
        when(filmService.findFilmById(id)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<FilmDTO> actualFilm = filmController.getFilmById(id);

        assertEquals(HttpStatus.NOT_FOUND, actualFilm.getStatusCode());
    }

    @Test
    public void getFilmByIdIOExceptionTest() throws Exception {
        int id = 1;
        when(filmService.findFilmById(id)).thenThrow(IOException.class);

        ResponseEntity<FilmDTO> actualFilm = filmController.getFilmById(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualFilm.getStatusCode());
    }

    @Test
    void getFilmByNameTest() throws IOException {
        String title = "A New Hope";
        List<FilmDTO> expectedFilms = Arrays.asList(new FilmDTO(), new FilmDTO());
        when(filmService.getFilmsByTitle(title)).thenReturn(expectedFilms);

        ResponseEntity<List<FilmDTO>> actualFilms = filmController.getFilmsByTitle(title);

        assertEquals(expectedFilms, actualFilms.getBody());

        verify(filmService, times(1)).getFilmsByTitle(title);
    }

    @Test
    public void getFilmByNameIOExceptionTest() throws Exception {
        String title = "A New Hope";
        when(filmService.getFilmsByTitle(title)).thenThrow(IOException.class);

        ResponseEntity<List<FilmDTO>> actualFilm = filmController.getFilmsByTitle(title);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualFilm.getStatusCode());
    }

    @Test
    public void getFilmByNameItemNotFoundExceptionTest() throws Exception {
        String title = "A New Hope";
        when(filmService.getFilmsByTitle(title)).thenThrow(ItemNotFoundException.class);

        ResponseEntity<List<FilmDTO>> actualFilm = filmController.getFilmsByTitle(title);

        assertEquals(HttpStatus.NOT_FOUND, actualFilm.getStatusCode());
    }

}
