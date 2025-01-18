package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

public interface StarshipService {
    ResponseWrapperPaged<StarshipDTO> getAllStarship(int page, int limit);
    StarshipDTO findStarshipById(int id);
}
