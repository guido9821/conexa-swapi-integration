package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    @GetMapping("/people/")
    public ResponseWrapperPaged<PeopleDTO> getAllPeople(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int limit){
        return peopleService.getAllPeople(page, limit);
    }

    @GetMapping("/people/{id}")
    public PeopleDTO getPersonById(@PathVariable int id) {
        return peopleService.findPeopleById(id);
    }

    @GetMapping("/people/searchByName/")
    public List<PeopleDTO> getPeopleByName(@RequestParam String name) {
        return peopleService.findPeopleByName(name);
    }
}
