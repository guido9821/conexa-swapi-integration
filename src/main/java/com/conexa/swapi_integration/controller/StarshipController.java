package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.StarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StarshipController {
    
    @Autowired
    private StarshipService starshipService;

    @GetMapping("/starships/")
    public ResponseWrapperPaged<StarshipDTO> getAllStarship(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int limit){
        return starshipService.getAllStarship(page, limit);
    }

    @GetMapping("/starships/{id}")
    public StarshipDTO findStarshipById(@PathVariable int id) {
        return starshipService.findStarshipById(id);
    }

    @GetMapping("/starships/searchByName/")
    public List<StarshipDTO> findStarshipsByName(@RequestParam String name) {
        return starshipService.findStarshipsByName(name);
    }

    @GetMapping("/starships/searchByModel/")
    public List<StarshipDTO> findStarshipsByModel(@RequestParam String model) {
        return starshipService.findStarshipsByModel(model);
    }
}
