package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.PeopleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    @GetMapping("/people/")
    @Operation(summary = "Obtiene todas las personas (paginación)", description = "Retorna una lista paginada de personas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = ResponseWrapperPaged.class))), // Documenta ResponseWrapperPaged
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseWrapperPaged<PeopleDTO>> getAllPeople(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int limit){

        try{
            ResponseWrapperPaged<PeopleDTO> responseWrapperPagedPeople = peopleService.getAllPeople(page, limit);
            return new ResponseEntity<>(responseWrapperPagedPeople, HttpStatus.OK);
        }catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/people/{id}")
    @Operation(summary = "Obtiene una persona por su ID", description = "Retorna la información de una persona específica según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = PeopleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PeopleDTO> getPersonById(@PathVariable int id) {
        try{
            PeopleDTO peopleDTO = peopleService.findPeopleById(id);
            return new ResponseEntity<>(peopleDTO,HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/people/searchByName/")
    @Operation(summary = "Busca personas por nombre", description = "Retorna una lista de personas que coinciden con el nombre proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PeopleDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PeopleDTO>> getPeopleByName(@RequestParam String name) {
        try {
            List<PeopleDTO> peopleDTOS =  peopleService.findPeopleByName(name);
            return new ResponseEntity<>(peopleDTOS,HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

    }
}
