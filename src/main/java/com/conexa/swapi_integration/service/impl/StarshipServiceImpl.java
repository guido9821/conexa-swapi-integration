package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.StarshipService;
import com.conexa.swapi_integration.util.MapperUtil;
import com.conexa.swapi_integration.util.ResponseWrapperUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class StarshipServiceImpl implements StarshipService {

    private final String BASE_URL_STARSHIP = "https://www.swapi.tech/api/starships/";
    private final RestTemplate restTemplate;

    public StarshipServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseWrapperPaged<StarshipDTO> getAllStarship(int page, int limit){
        ResponseEntity<ResponseWrapperPaged<StarshipDTO>> responseEntity = restTemplate.exchange(BASE_URL_STARSHIP + "?page=" + page + "&limit=" + limit,
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseWrapperPaged<StarshipDTO>>() {});
        if( responseEntity.getBody() != null && responseEntity.getBody().getResults() != null){
            return responseEntity.getBody();
        }
        return null;
    }


    @Override
    public StarshipDTO findStarshipById(int id) {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_STARSHIP + id, HttpMethod.GET, null, String.class);
            return  MapperUtil.getObjectFromJson(responseEntityRaw, StarshipDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StarshipDTO> findStarshipsByName(String name) {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_STARSHIP + "?name=" + name, HttpMethod.GET, null, String.class);
            return MapperUtil.getObjectListFromJson(responseEntityRaw, StarshipDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StarshipDTO> findStarshipsByModel(String model) {
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_STARSHIP + "searchByModel/?model=" + model, HttpMethod.GET, null, String.class);
        try {
            return MapperUtil.getObjectListFromJson(responseEntityRaw, StarshipDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
