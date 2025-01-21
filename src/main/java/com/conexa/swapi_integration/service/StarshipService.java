package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

import java.io.IOException;
import java.util.List;

public interface StarshipService {
    ResponseWrapperPaged<StarshipDTO> getAllStarship(int page, int limit);
    StarshipDTO findStarshipById(int id) throws IOException;
    List<StarshipDTO> findStarshipsByName(String name) throws IOException;
    List<StarshipDTO> findStarshipsByModel(String model) throws IOException;
}
