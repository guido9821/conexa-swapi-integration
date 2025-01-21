package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

import java.util.List;

public interface PeopleService {
        ResponseWrapperPaged<PeopleDTO> getAllPeople(int page, int limit);
        PeopleDTO findPeopleById(int id);
        List<PeopleDTO> findPeopleByName(String name);
    }