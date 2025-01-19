package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.StarshipService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class StarshipServiceImpl implements StarshipService {

    private final String BASE_URL_STARSHIP = "https://www.swapi.tech/api/starships/";
    private final RestTemplate restTemplate = new RestTemplate();

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
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_STARSHIP + id, HttpMethod.GET, null, String.class);
        System.out.println("Raw Response:\n" + responseEntityRaw.getBody());
        try {
            ResponseWrapper<StarshipDTO> responseWrapper = ResponseWrapper.fromJson(responseEntityRaw.getBody(), StarshipDTO.class);
            if(responseWrapper.getResultDTO() != null ){
                return responseWrapper.getResultDTO().getProperties();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
