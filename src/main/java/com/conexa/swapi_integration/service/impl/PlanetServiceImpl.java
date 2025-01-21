package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PlanetService;
import com.conexa.swapi_integration.util.MapperUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    public PlanetDTO findPlanetById(int id) {
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_PLANETS + id, HttpMethod.GET, null, String.class);
        try {
            ResponseWrapper<PlanetDTO> responseWrapper = ResponseWrapper.fromJson(responseEntityRaw.getBody(), PlanetDTO.class);
                return responseWrapper.getResultDTO().getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlanetDTO> findPlanetByName(String name) {
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_PLANETS + "?name=" + name, HttpMethod.GET, null, String.class);
        try {
            return MapperUtil.getObjectListFromJson(responseEntityRaw, PlanetDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
