package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.FilmDTO;

import java.io.IOException;
import java.util.List;

public interface FilmService {
    List<FilmDTO> getAllFilms();
    FilmDTO findFilmById(int id) throws IOException;
    List<FilmDTO> getFilmsByTitle(String title) throws IOException;
}
