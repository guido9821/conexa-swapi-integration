package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

public interface SpeciesService {
    ResponseWrapperPaged<SpeciesDTO> getAllSpecies(int page, int limit);
    SpeciesDTO findSpeciesById(int id);
}
