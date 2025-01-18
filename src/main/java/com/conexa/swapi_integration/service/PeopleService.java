package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;

public interface PeopleService {
        ResponseWrapperPaged<PeopleDTO> getAllPeople(int page, int limit);
        PeopleDTO findPeopleById(int id);
    }
