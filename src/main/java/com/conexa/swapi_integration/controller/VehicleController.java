package com.conexa.swapi_integration.controller;


import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.dto.VehicleDTO;
import com.conexa.swapi_integration.exceptions.ItemNotFoundException;
import com.conexa.swapi_integration.model.ResponseWrapperPaged;
import com.conexa.swapi_integration.service.VehicleService;
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
@Tag(name = "Vehicles", description = "Operaciones relacionadas con vehículos")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles/")
    @Operation(summary = "Obtiene todos los vehículos (paginación)", description = "Retorna una lista paginada de vehículos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = ResponseWrapperPaged.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseWrapperPaged<VehicleDTO>> getAllVehicle(
            @Parameter(description = "Número de página (por defecto: 1)", example = "1") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Cantidad de elementos por página (por defecto: 5)", example = "5") @RequestParam(defaultValue = "5") int limit) {
        try{
            ResponseWrapperPaged<VehicleDTO> vehicleDTOResponseWrapperPaged = vehicleService.getAllVehicle(page, limit);
            return new ResponseEntity<>(vehicleDTOResponseWrapperPaged, HttpStatus.OK);
        }catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/vehicles/{id}")
    @Operation(summary = "Obtiene un vehículo por su ID", description = "Retorna la información de un vehículo específico según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = VehicleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<VehicleDTO> findVehicleById(@Parameter(description = "ID del vehículo a buscar", example = "4", required = true) @PathVariable int id) throws IOException {
        try {
            VehicleDTO vehicleDTO = vehicleService.findVehicleById(id);
            return new ResponseEntity<>(vehicleDTO,HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/vehicles/searchByName/")
    @Operation(summary = "Busca vehículos por nombre", description = "Retorna una lista de vehículos que coinciden con el nombre proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<VehicleDTO>> findVehiclesByName(@Parameter(description = "Nombre del vehículo a buscar", example = "Sand Crawler", required = true) @RequestParam String name) throws IOException {
        try {
            List<VehicleDTO> vehicleDTOS = vehicleService.findVehiclesByName(name);
            return new ResponseEntity<>(vehicleDTOS, HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/vehicles/searchByModel/")
    @Operation(summary = "Busca vehículos por modelo", description = "Retorna una lista de vehículos que coinciden con el modelo proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<VehicleDTO>> findVehiclesByModel(@Parameter(description = "Modelo del vehículo a buscar", example = "Digger Crawler", required = true) @RequestParam String model) throws IOException {
        try {
            List<VehicleDTO> vehicleDTOS = vehicleService.findVehiclesByModel(model);
            return new ResponseEntity<>(vehicleDTOS, HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
