package com.conexa.swapi_integration.controllerTest;

import com.conexa.swapi_integration.controller.FilmController;
import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.service.FilmService;
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

        FilmDTO actualFilm = filmController.getFilmById(id);

        assertEquals(expectedFilm, actualFilm);

        verify(filmService, times(1)).findFilmById(id);
    }

    @Test
    void getFilmByNameTest() {
        String title = "A New Hope";
        List<FilmDTO> expectedFilms = Arrays.asList(new FilmDTO(), new FilmDTO());
        when(filmService.getFilmsByTitle(title)).thenReturn(expectedFilms);

        List<FilmDTO> actualFilms = filmController.getFilmsByTitle(title);

        assertEquals(expectedFilms, actualFilms);

        verify(filmService, times(1)).getFilmsByTitle(title);
    }

}
