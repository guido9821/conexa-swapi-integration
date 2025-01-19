package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.SpeciesService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    private final String BASE_URL_SPECIES = "https://www.swapi.tech/api/species/";
    private final RestTemplate restTemplate = new RestTemplate();

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
    public SpeciesDTO findSpeciesById(int id) {
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_SPECIES + id, HttpMethod.GET, null, String.class);
        System.out.println("Raw Response:\n" + responseEntityRaw.getBody());
        try {
            ResponseWrapper<SpeciesDTO> responseWrapper = ResponseWrapper.fromJson(responseEntityRaw.getBody(), SpeciesDTO.class);
            if(responseWrapper.getResultDTO() != null ){
                return responseWrapper.getResultDTO().getProperties();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

