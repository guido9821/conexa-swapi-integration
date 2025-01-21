package com.conexa.swapi_integration.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Datos de un vehículo del universo de Star Wars")
public class VehicleDTO {

    @Schema(description = "Nombre del vehículo", example = "Sand Crawler")
    private String name;

    @Schema(description = "Modelo del vehículo", example = "Digger Crawler")
    private String model;

    @Schema(description = "Fabricante del vehículo", example = "Corellia Mining Corporation")
    private String manufacturer;

    @Schema(description = "Costo del vehículo en créditos", example = "150000")
    @JsonProperty("cost_in_credits")
    private String costInCredits;

    @Schema(description = "Longitud del vehículo", example = "36.8")
    private String length;

    @Schema(description = "Velocidad atmosférica máxima del vehículo", example = "30")
    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;

    @Schema(description = "Tripulación del vehículo", example = "46")
    private String crew;

    @Schema(description = "Número de pasajeros que puede transportar el vehículo", example = "30")
    private String passengers;

    @Schema(description = "Capacidad de carga del vehículo", example = "50000")
    @JsonProperty("cargo_capacity")
    private String cargoCapacity;

    @Schema(description = "Clase del vehículo", example = "wheeled")
    @JsonProperty("vehicle_class")
    private String vehicleClass;

    @Schema(description = "Consumibles del vehículo", example = "2 months")
    private String consumables;

    @Schema(description = "Lista de URLs de las películas en las que aparece el vehículo")
    private List<String> films;

    @Schema(description = "Lista de URLs de los pilotos del vehículo")
    private List<String> pilots;

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    public List<String> getPilots() {
        return pilots;
    }

    public void setPilots(List<String> pilots) {
        this.pilots = pilots;
    }

    public String getConsumables() {
        return consumables;
    }

    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCostInCredits() {
        return costInCredits;
    }

    public void setCostInCredits(String costInCredits) {
        this.costInCredits = costInCredits;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getMaxAtmospheringSpeed() {
        return maxAtmospheringSpeed;
    }

    public void setMaxAtmospheringSpeed(String maxAtmospheringSpeed) {
        this.maxAtmospheringSpeed = maxAtmospheringSpeed;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(String cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }
}
