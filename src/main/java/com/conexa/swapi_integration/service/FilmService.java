package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;

import java.util.List;

public interface FilmService {
    List<FilmDTO> getAllFilms();
    FilmDTO findFilmById(int id);
}
