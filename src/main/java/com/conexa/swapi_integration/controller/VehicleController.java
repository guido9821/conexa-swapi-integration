package com.conexa.swapi_integration.controller;


import com.conexa.swapi_integration.dto.VehicleDTO;
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
import org.springframework.web.bind.annotation.*;

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
    public ResponseWrapperPaged<VehicleDTO> getAllVehicle(
            @Parameter(description = "Número de página (por defecto: 1)", example = "1") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Cantidad de elementos por página (por defecto: 5)", example = "5") @RequestParam(defaultValue = "5") int limit) {
        return vehicleService.getAllVehicle(page, limit);
    }

    @GetMapping("/vehicles/{id}")
    @Operation(summary = "Obtiene un vehículo por su ID", description = "Retorna la información de un vehículo específico según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(schema = @Schema(implementation = VehicleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public VehicleDTO findVehicleById(@Parameter(description = "ID del vehículo a buscar", example = "4", required = true) @PathVariable int id) {
        return vehicleService.findVehicleById(id);
    }

    @GetMapping("/vehicles/searchByName/")
    @Operation(summary = "Busca vehículos por nombre", description = "Retorna una lista de vehículos que coinciden con el nombre proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<VehicleDTO> findVehiclesByName(@Parameter(description = "Nombre del vehículo a buscar", example = "Sand Crawler", required = true) @RequestParam String name) {
        return vehicleService.findVehiclesByName(name);
    }

    @GetMapping("/vehicles/searchByModel/")
    @Operation(summary = "Busca vehículos por modelo", description = "Retorna una lista de vehículos que coinciden con el modelo proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<VehicleDTO> findVehiclesByModel(@Parameter(description = "Modelo del vehículo a buscar", example = "Digger Crawler", required = true) @RequestParam String model) {
        return vehicleService.findVehiclesByModel(model);
    }
}
