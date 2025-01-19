package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PeopleService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final String BASE_URL_PEOPLES = "http://www.swapi.tech/api/people/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseWrapperPaged<PeopleDTO> getAllPeople(int page, int limit){
        ResponseEntity<ResponseWrapperPaged<PeopleDTO>> responseEntity = restTemplate.exchange(BASE_URL_PEOPLES + "?page=" + page + "&limit=" + limit,
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseWrapperPaged<PeopleDTO>>() {});
        if( responseEntity.getBody() != null && responseEntity.getBody().getResults() != null){
            return responseEntity.getBody();
        }
        return null;
    }

    @Override
    public PeopleDTO findPeopleById(int id) {
        ResponseEntity<ResponseWrapper<PeopleDTO>> responseEntity = restTemplate.exchange(BASE_URL_PEOPLES  + id,
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseWrapper<PeopleDTO>>() {});
        if (responseEntity.getBody() != null) {
            return responseEntity.getBody().getResult().getProperties();
        }else{
            System.err.println("No se pudo obtener el resultado.");
        }
        return null;
    }
}
