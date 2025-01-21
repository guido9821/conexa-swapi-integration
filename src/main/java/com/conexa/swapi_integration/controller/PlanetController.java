package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.PlanetDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PlanetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Planets", description = "Operaciones relacionadas con planetas")
public class PlanetController {
    
    @Autowired
    PlanetService planetService;

    @GetMapping("/planets/")
    @Operation(summary = "Obtiene todos los planetas (paginación)", description = "Retorna una lista paginada de planetas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = ResponseWrapperPaged.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseWrapperPaged<PlanetDTO>> getAllPlanets(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int limit){
        try {
            ResponseWrapperPaged<PlanetDTO> responseWrapperPagedPlanet = planetService.getAllPlanets(page, limit);
            return new ResponseEntity<>(responseWrapperPagedPlanet, HttpStatus.OK);
        }catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/planets/{id}")
    @Operation(summary = "Obtiene un planeta por su ID", description = "Retorna la información de un planeta específico según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = PlanetDTO.class))),
            @ApiResponse(responseCode = "404", description = "Planeta no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PlanetDTO> findPlanetById(@PathVariable int id) {
        try {
            PlanetDTO planetDTO = planetService.findPlanetById(id);
            return new ResponseEntity<>(planetDTO, HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Busca planetas por nombre", description = "Retorna una lista de planetas que coinciden con el nombre proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanetDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/planets/searchByName/")
    public ResponseEntity<List<PlanetDTO>> findPlanetByName(@RequestParam String name) {
        try {
            List<PlanetDTO> planetDTOS = planetService.findPlanetByName(name);
            return new ResponseEntity<>(planetDTOS, HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
