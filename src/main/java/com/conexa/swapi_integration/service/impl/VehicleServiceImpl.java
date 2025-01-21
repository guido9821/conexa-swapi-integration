package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.VehicleService;
import com.conexa.swapi_integration.util.MapperUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final String BASE_URL_VEHICLES = "https://www.swapi.tech/api/vehicles/";
    private final RestTemplate restTemplate;

    public VehicleServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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

    @Override
    public List<VehicleDTO> findVehiclesByName(String name) {
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_VEHICLES + "?name=" + name, HttpMethod.GET, null, String.class);
        try {
            return MapperUtil.getObjectListFromJson(responseEntityRaw, VehicleDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<VehicleDTO> findVehiclesByModel(String model) {
        ResponseEntity<String> responseEntityRaw = restTemplate.exchange(
                BASE_URL_VEHICLES + "?model=" + model, HttpMethod.GET, null, String.class);
        try {
            return MapperUtil.getObjectListFromJson(responseEntityRaw, VehicleDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
