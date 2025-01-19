package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlanetController {
    
    @Autowired
    PlanetService planetService;

    @GetMapping("/planets/")
    public ResponseWrapperPaged<PlanetDTO> getAllSpecies(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int limit){
        return planetService.getAllPlanets(page, limit);
    }

    @GetMapping("/planets/{id}")
    public PlanetDTO getPersonById(@PathVariable int id) {
        return planetService.findPlanetById(id);
    }

    @GetMapping("/planets/searchByName/")
    public List<PlanetDTO> getSpeciesByModel(@RequestParam String name) {
        return planetService.findPlanetByName(name);
    } 
    
}
