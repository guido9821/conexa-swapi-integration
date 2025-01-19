package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.VehicleService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final String BASE_URL_VEHICLES = "https://www.swapi.tech/api/vehicles/";
    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public ResponseWrapperPaged<VehicleDTO> getAllVehicle(int page, int limit){
        ResponseEntity<ResponseWrapperPaged<VehicleDTO>> responseEntity = restTemplate.exchange(BASE_URL_VEHICLES + "?page=" + page + "&limit=" + limit,
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseWrapperPaged<VehicleDTO>>() {});
        if( responseEntity.getBody() != null && responseEntity.getBody().getResults() != null){
            return responseEntity.getBody();
        }
        return null;
    }

    @Override
    public VehicleDTO findVehicleById(int id) {

        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_VEHICLES + id, HttpMethod.GET, null, String.class);
        System.out.println("Raw Response:\n" + responseEntityRaw.getBody());
        try {
            ResponseWrapper<VehicleDTO> responseWrapper = ResponseWrapper.fromJson(responseEntityRaw.getBody(), VehicleDTO.class);
            if(responseWrapper.getResultDTO() != null ){
                return responseWrapper.getResultDTO().getProperties();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
