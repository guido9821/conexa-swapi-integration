package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.StarshipService;
import com.conexa.swapi_integration.util.MapperUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
    public StarshipDTO findStarshipById(int id) throws IOException {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_STARSHIP + id, HttpMethod.GET, null, String.class);
            return  MapperUtil.getObjectFromJson(responseEntityRaw, StarshipDTO.class);
        }catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new ItemNotFoundException("Nave espacial no encontrada con ID: " + id);
            }
            throw new RuntimeException("Error al comunicarse con el servicio externo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<StarshipDTO> findStarshipsByName(String name) throws IOException {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_STARSHIP + "?name=" + name, HttpMethod.GET, null, String.class);
            return MapperUtil.getObjectListFromJson(responseEntityRaw, StarshipDTO.class);
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new ItemNotFoundException("Nave espacial no encontrada con nombre: " + name);
            }
            throw new RuntimeException("Error al comunicarse con el servicio externo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<StarshipDTO> findStarshipsByModel(String model) throws IOException {
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_STARSHIP + "searchByModel/?model=" + model, HttpMethod.GET, null, String.class);
        try {
            return MapperUtil.getObjectListFromJson(responseEntityRaw, StarshipDTO.class);
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new ItemNotFoundException("Nave espacial no encontrada con modelo: " + model);
            }
            throw new RuntimeException("Error al comunicarse con el servicio externo: " + e.getMessage(), e);
        }
    }
}
