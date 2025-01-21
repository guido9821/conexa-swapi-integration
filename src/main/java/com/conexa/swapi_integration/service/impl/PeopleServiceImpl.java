package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PeopleService;
import com.conexa.swapi_integration.util.MapperUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final String BASE_URL_PEOPLE = "https://www.swapi.tech/api/people/";
    private final RestTemplate restTemplate;

    public PeopleServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
        try {
             ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_PEOPLE + id, HttpMethod.GET, null, String.class);
            return MapperUtil.getObjectFromJson(responseEntityRaw, PeopleDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PeopleDTO> findPeopleByName(String name) {
        try {
            ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_PEOPLE + "?name=" +name, HttpMethod.GET, null, String.class);

            return MapperUtil.getObjectListFromJson(responseEntityRaw, PeopleDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
