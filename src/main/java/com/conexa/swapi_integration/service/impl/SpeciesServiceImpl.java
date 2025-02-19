package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.SpeciesService;
import com.conexa.swapi_integration.util.MapperUtil;
import com.conexa.swapi_integration.util.ResponseWrapperUtil;
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
public class SpeciesServiceImpl implements SpeciesService {

    private final String BASE_URL_SPECIES = "https://www.swapi.tech/api/species/";
    private final RestTemplate restTemplate;

    public SpeciesServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseWrapperPaged<SpeciesDTO> getAllSpecies(int page, int limit){
        ResponseEntity<ResponseWrapperPaged<SpeciesDTO>> responseEntity = restTemplate.exchange(BASE_URL_SPECIES + "?page=" + page + "&limit=" + limit,
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseWrapperPaged<SpeciesDTO>>() {});
        if( responseEntity.getBody() != null && responseEntity.getBody().getResults() != null){
            return responseEntity.getBody();
        }
        return null;
    }


    @Override
    public SpeciesDTO findSpeciesById(int id) throws IOException {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_SPECIES + id, HttpMethod.GET, null, String.class);
                return MapperUtil.getObjectFromJson(responseEntityRaw,SpeciesDTO.class);
        }catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new ItemNotFoundException("Especie no encontrado con ID: " + id);
            }
            throw new RuntimeException("Error al comunicarse con el servicio externo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SpeciesDTO> findSpeciesByName(String name) throws IOException {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_SPECIES + "?name=" + name, HttpMethod.GET, null, String.class);
            return MapperUtil.getObjectListFromJson(responseEntityRaw, SpeciesDTO.class);
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new ItemNotFoundException("Especie no encontrado con nombre: " + name);
            }
            throw new RuntimeException("Error al comunicarse con el servicio externo: " + e.getMessage(), e);
        }
    }
}

