package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Films", description = "Operaciones relacionadas con películas")
public class FilmController {

    @Autowired
    FilmService filmService;

    @GetMapping("/films/")
    @Operation(summary = "Obtiene todas las películas", description = "Retorna una lista de todas las películas disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FilmDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<FilmDTO> getAllFilms(){
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    @Operation(summary = "Obtiene una película por su ID", description = "Retorna la información de una película específica según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = FilmDTO.class))),
            @ApiResponse(responseCode = "404", description = "Película no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public FilmDTO getFilmById(@PathVariable int id) {
        return filmService.findFilmById(id);
    }

    @Operation(summary = "Busca películas por título", description = "Retorna una lista de películas que coinciden con el título proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FilmDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/films/searchByTitle/")
    public List<FilmDTO> getFilmsByTitle(@RequestParam String title) {
        return filmService.getFilmsByTitle(title);
    }
}
