package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FilmController {

    @Autowired
    FilmService filmService;

    @GetMapping("/films/")
    public List<FilmDTO> getAllFilms(){
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public FilmDTO getFilmById(@PathVariable int id) {
        return filmService.findFilmById(id);
    }

    @GetMapping("/films/searchByTitle/")
    public List<FilmDTO> getFilmsByTitle(@RequestParam String title) {
        return filmService.getFilmsByTitle(title);
    }
}
