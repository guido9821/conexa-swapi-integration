package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.FilmService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class FilmServiceImpl implements FilmService{

    private final String BASE_URL_FILMS = "https://www.swapi.tech/api/films/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseWrapperPaged<FilmDTO> getAllFilms(int page, int limit){
        ResponseEntity<ResponseWrapperPaged<FilmDTO>> responseEntity = restTemplate.exchange(BASE_URL_FILMS + "?page=" + page + "&limit=" + limit,
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseWrapperPaged<FilmDTO>>() {});
        if( responseEntity.getBody() != null && responseEntity.getBody().getResults() != null){
            return responseEntity.getBody();
        }
        return null;
    }

    @Override
    public FilmDTO findFilmById(int id) {
        return restTemplate.getForObject(BASE_URL_FILMS + "/" + id, FilmDTO.class);
    }
}
