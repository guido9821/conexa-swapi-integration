package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

import java.io.IOException;
import java.util.List;

public interface SpeciesService {
    ResponseWrapperPaged<SpeciesDTO> getAllSpecies(int page, int limit);
    SpeciesDTO findSpeciesById(int id) throws IOException;
    List<SpeciesDTO> findSpeciesByName(String name) throws IOException;
}
