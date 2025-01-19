package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.dto.ResultDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.FilmService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService{

    private final String BASE_URL_FILMS = "https://www.swapi.tech/api/films/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<FilmDTO> getAllFilms(){
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                    BASE_URL_FILMS, HttpMethod.GET, null, String.class);

            // Deserializar la respuesta JSON
            ResponseWrapper<FilmDTO> responseWrapper = ResponseWrapper.fromJson(responseEntityRaw.getBody(), FilmDTO.class);

            // Verificar y convertir la lista de resultados
            if (responseWrapper.getResultDTOList() != null) {
                return getFilmList(responseWrapper.getResultDTOList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la respuesta de la API: " + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public FilmDTO findFilmById(int id) {
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_FILMS + id, HttpMethod.GET, null, String.class);
        System.out.println("Raw Response:\n" + responseEntityRaw.getBody());
        try {
            ResponseWrapper<FilmDTO> responseWrapper = ResponseWrapper.fromJson(responseEntityRaw.getBody(), FilmDTO.class);
            if(responseWrapper.getResultDTO() != null ){
                return responseWrapper.getResultDTO().getProperties();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private List<FilmDTO> getFilmList(List<ResultDTO<FilmDTO>> resultDTOList){
        List<FilmDTO> filmList = new ArrayList<>();
        resultDTOList.forEach(resultDTO -> {
            filmList.add(resultDTO.getProperties());
        });
        return filmList;
    }
}
