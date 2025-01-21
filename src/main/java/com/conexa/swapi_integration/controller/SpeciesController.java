package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.SpeciesDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.SpeciesService;
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
@Tag(name = "Species", description = "Operaciones relacionadas con especies")
public class SpeciesController {
    @Autowired
    SpeciesService speciesService;

    @Operation(summary = "Obtiene todas las especies (paginación)", description = "Retorna una lista paginada de especies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = ResponseWrapperPaged.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/species/")
    public ResponseEntity<ResponseWrapperPaged<SpeciesDTO>> getAllSpecies(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int limit){
        try {
            ResponseWrapperPaged<SpeciesDTO> responseWrapperPagedSpecies =  speciesService.getAllSpecies(page, limit);
            return new ResponseEntity<>(responseWrapperPagedSpecies, HttpStatus.OK);
        }catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/species/{id}")
    @Operation(summary = "Obtiene una especie por su ID", description = "Retorna la información de una especie específica según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = SpeciesDTO.class))),
            @ApiResponse(responseCode = "404", description = "Especie no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<SpeciesDTO> findSpeciesById(@PathVariable int id) throws IOException {
        try {
            SpeciesDTO speciesDTO = speciesService.findSpeciesById(id);
            return new ResponseEntity<>(speciesDTO, HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/species/searchByName/")
    @Operation(summary = "Busca especies por nombre", description = "Retorna una lista de especies que coinciden con el nombre proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SpeciesDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<SpeciesDTO>> findSpeciesByName(@RequestParam String name) {
        try {
            List<SpeciesDTO> speciesDTOS = speciesService.findSpeciesByName(name);
            return new ResponseEntity<>(speciesDTOS,HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
