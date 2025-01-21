package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.service.FilmService;
import com.conexa.swapi_integration.util.MapperUtil;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService{

    private final String BASE_URL_FILMS = "https://www.swapi.tech/api/films/";
    private final RestTemplate restTemplate;

    public FilmServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<FilmDTO> getAllFilms(){
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                    BASE_URL_FILMS, HttpMethod.GET, null, String.class);

            return MapperUtil.getObjectListFromJson(responseEntityRaw, FilmDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la respuesta de la API: " + e.getMessage(), e);
        }}

    @Override
    public FilmDTO findFilmById(int id) {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                    BASE_URL_FILMS + id, HttpMethod.GET, null, String.class);
            return MapperUtil.getObjectFromJson(responseEntityRaw, FilmDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FilmDTO>getFilmsByTitle(String title) {
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_FILMS + "?title=" + title, HttpMethod.GET, null, String.class);
        try {
            return MapperUtil.getObjectListFromJson(responseEntityRaw, FilmDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
