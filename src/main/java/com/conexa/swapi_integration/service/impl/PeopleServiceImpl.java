package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PeopleService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final String BASE_URL_PEOPLE = "http://www.swapi.tech/api/people/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseWrapperPaged<PeopleDTO> getAllPeople(int page, int limit){
        ResponseEntity<ResponseWrapperPaged<PeopleDTO>> responseEntity = restTemplate.exchange(BASE_URL_PEOPLE + "?page=" + page + "&limit=" + limit,
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseWrapperPaged<PeopleDTO>>() {});
        if( responseEntity.getBody() != null && responseEntity.getBody().getResults() != null){
            return responseEntity.getBody();
        }
        return null;
    }

    @Override
    public PeopleDTO findPeopleById(int id) {

        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_PEOPLE + id, HttpMethod.GET, null, String.class);
        System.out.println("Raw Response:\n" + responseEntityRaw.getBody());
        try {
            ResponseWrapper<PeopleDTO> responseWrapper = ResponseWrapper.fromJson(responseEntityRaw.getBody(), PeopleDTO.class);
            if(responseWrapper.getResultDTO() != null ){
                return responseWrapper.getResultDTO().getProperties();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
