package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.StarshipService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        return restTemplate.getForObject(BASE_URL_STARSHIP + "/" + id, StarshipDTO.class);

    }
}
