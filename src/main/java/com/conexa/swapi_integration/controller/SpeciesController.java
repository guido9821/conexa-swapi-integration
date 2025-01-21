package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SpeciesController {
    @Autowired
    SpeciesService speciesService;

    @GetMapping("/species/")
    public ResponseWrapperPaged<SpeciesDTO> getAllSpecies(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int limit){
        return speciesService.getAllSpecies(page, limit);
    }

    @GetMapping("/species/{id}")
    public SpeciesDTO findSpeciesById(@PathVariable int id) {
        return speciesService.findSpeciesById(id);
    }

    @GetMapping("/species/searchByName/")
    public List<SpeciesDTO> findSpeciesByName(@RequestParam String name) {
        return speciesService.findSpeciesByName(name);
    }
}
