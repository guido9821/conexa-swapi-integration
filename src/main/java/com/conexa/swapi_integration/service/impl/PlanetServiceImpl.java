package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PlanetService;
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
public class PlanetServiceImpl implements PlanetService {

    private final String BASE_URL_PLANETS = "https://www.swapi.tech/api/planets/";
    private final RestTemplate restTemplate;

    public PlanetServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Override
    public ResponseWrapperPaged<PlanetDTO> getAllPlanets(int page, int limit) {
        ResponseEntity<ResponseWrapperPaged<PlanetDTO>> responseEntity = restTemplate.exchange(BASE_URL_PLANETS + "?page=" + page + "&limit=" + limit,
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseWrapperPaged<PlanetDTO>>() {});
        if( responseEntity.getBody() != null && responseEntity.getBody().getResults() != null){
            return responseEntity.getBody();
        }
        return null;
    }

    @Override
    public PlanetDTO findPlanetById(int id) throws IOException {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_PLANETS + id, HttpMethod.GET, null, String.class);
                return MapperUtil.getObjectFromJson(responseEntityRaw, PlanetDTO.class);
        }catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new ItemNotFoundException("Planeta no encontrado con ID: " + id);
            }
            throw new RuntimeException("Error al comunicarse con el servicio externo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PlanetDTO> findPlanetByName(String name) throws IOException {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                    BASE_URL_PLANETS + "?name=" + name, HttpMethod.GET, null, String.class);
            return MapperUtil.getObjectListFromJson(responseEntityRaw, PlanetDTO.class);
        }catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new ItemNotFoundException("Planeta no encontrado con nombre " + name);
            }
            throw new RuntimeException("Error al comunicarse con el servicio externo: " + e.getMessage(), e);
        }
    }
}
