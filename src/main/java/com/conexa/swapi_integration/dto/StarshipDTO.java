package com.conexa.swapi_integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StarshipDTO {

    @Schema(description = "Nombre de la nave espacial", example = "Millennium Falcon")
    private String name;

    @Schema(description = "Modelo de la nave espacial", example = "YT-1300 light freighter")
    private String model;

    @Schema(description = "Fabricante de la nave espacial", example = "Corellian Engineering Corporation")
    private String manufacturer;

    @Schema(description = "Costo de la nave en créditos", example = "100000")
    @JsonProperty("cost_in_credits")
    private String costInCredits;

    @Schema(description = "Longitud de la nave", example = "34.37")
    private String length;

    @Schema(description = "Velocidad atmosférica máxima de la nave", example = "1050")
    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;

    @Schema(description = "Tripulación de la nave", example = "4")
    private String crew;

    @Schema(description = "Número de pasajeros que puede transportar la nave", example = "6")
    private String passengers;

    @Schema(description = "Capacidad de carga de la nave", example = "100000")
    @JsonProperty("cargo_capacity")
    private String cargoCapacity;

    @Schema(description = "Clase de nave estelar", example = "Light freighter")
    @JsonProperty("starship_class")
    private String starshipClass;

    @Schema(description = "Megalineas por hora", example = "75")
    private String MGLT;

    @Schema(description = "Lista de URLs de los pilotos de la nave")
    private List<String> pilots;

    public String getMGLT() {
        return MGLT;
    }

    public void setMGLT(String MGLT) {
        this.MGLT = MGLT;
    }

    public List<String> getPilots() {
        return pilots;
    }

    public void setPilots(List<String> pilots) {
        this.pilots = pilots;
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

    public String getStarshipClass() {
        return starshipClass;
    }

    public void setStarshipClass(String starshipClass) {
        this.starshipClass = starshipClass;
    }
}
