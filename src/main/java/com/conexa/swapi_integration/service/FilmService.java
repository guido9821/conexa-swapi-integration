package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

public interface FilmService {
    ResponseWrapperPaged<FilmDTO> getAllFilms(int page, int limit);
    FilmDTO findFilmById(int id);
}
