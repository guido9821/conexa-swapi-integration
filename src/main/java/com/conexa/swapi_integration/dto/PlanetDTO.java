package com.conexa.swapi_integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Datos de un planeta del universo de Star Wars")
public class PlanetDTO {

    @Schema(description = "Nombre del planeta", example = "Tatooine")
    private String name;

    @Schema(description = "Diámetro del planeta", example = "10465")
    private String diameter;

    @Schema(description = "Período de rotación del planeta", example = "23")
    @JsonProperty("rotation_period")
    private String rotationPeriod;

    @Schema(description = "Período orbital del planeta", example = "304")
    @JsonProperty("orbital_period")
    private String orbitalPeriod;

    @Schema(description = "Gravedad del planeta", example = "1 standard")
    private String gravity;

    @Schema(description = "Población del planeta", example = "200000")
    private String population;

    @Schema(description = "Clima del planeta", example = "arid")
    private String climate;

    @Schema(description = "Terreno del planeta", example = "desert")
    private String terrain;

    @Schema(description = "Porcentaje de superficie cubierta por agua", example = "1")
    @JsonProperty("surface_water")
    private String surfaceWater;

    @Schema(description = "Fecha de creación del registro", example = "2014-12-10T11:35:48.000000Z")
    private String created;

    @Schema(description = "Fecha de la última edición del registro", example = "2014-12-20T20:58:18.420000Z")
    private String edited;

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getRotationPeriod() {
        return rotationPeriod;
    }

    public void setRotationPeriod(String rotationPeriod) {
        this.rotationPeriod = rotationPeriod;
    }

    public String getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(String orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public String getGravity() {
        return gravity;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getSurfaceWater() {
        return surfaceWater;
    }

    public void setSurfaceWater(String surfaceWater) {
        this.surfaceWater = surfaceWater;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
