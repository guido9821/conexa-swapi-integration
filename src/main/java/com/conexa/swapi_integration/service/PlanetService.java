package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

import java.io.IOException;
import java.util.List;

public interface PlanetService {

    ResponseWrapperPaged<PlanetDTO> getAllPlanets(int page, int limit);
    PlanetDTO findPlanetById(int id) throws IOException;
    List<PlanetDTO> findPlanetByName(String name) throws IOException;
}
