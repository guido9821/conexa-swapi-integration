package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.StarshipDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.StarshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Starships", description = "Operaciones relacionadas con naves espaciales")
public class StarshipController {

    @Autowired
    private StarshipService starshipService;

    @GetMapping("/starships/")
    @Operation(summary = "Obtiene todas las naves espaciales (paginación)", description = "Retorna una lista paginada de naves espaciales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = ResponseWrapperPaged.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseWrapperPaged<StarshipDTO>> getAllStarship(
            @Parameter(description = "Número de página (por defecto: 1)", example = "1") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Cantidad de elementos por página (por defecto: 5)", example = "5") @RequestParam(defaultValue = "5") int limit) {
        try{
            ResponseWrapperPaged<StarshipDTO> starshipDTOResponseWrapperPaged = starshipService.getAllStarship(page, limit);
            return new ResponseEntity<>(starshipDTOResponseWrapperPaged, HttpStatus.OK);
        }catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/starships/{id}")
    @Operation(summary = "Obtiene una nave espacial por su ID", description = "Retorna la información de una nave espacial específica según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = StarshipDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nave espacial no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<StarshipDTO> findStarshipById(@Parameter(description = "ID de la nave espacial a buscar", example = "1", required = true) @PathVariable int id) throws IOException {
        try {
            StarshipDTO starshipDTO = starshipService.findStarshipById(id);
            return new ResponseEntity<>(starshipDTO,HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/starships/searchByName/")
    @Operation(summary = "Busca naves espaciales por nombre", description = "Retorna una lista de naves espaciales que coinciden con el nombre proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StarshipDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<StarshipDTO>> findStarshipsByName(@Parameter(description = "Nombre de la nave espacial a buscar", example = "Millennium Falcon", required = true) @RequestParam String name) throws IOException {
        try {
            List<StarshipDTO> starshipDTOS = starshipService.findStarshipsByName(name);
            return new ResponseEntity<>(starshipDTOS, HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/starships/searchByModel/")
    @Operation(summary = "Busca naves espaciales por modelo", description = "Retorna una lista de naves espaciales que coinciden con el modelo proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StarshipDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<StarshipDTO>> findStarshipsByModel(@Parameter(description = "Modelo de la nave espacial a buscar", example = "YT-1300 light freighter", required = true) @RequestParam String model) throws IOException {
        try {
            List<StarshipDTO> starshipDTOS = starshipService.findStarshipsByModel(model);
            return new ResponseEntity<>(starshipDTOS, HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
