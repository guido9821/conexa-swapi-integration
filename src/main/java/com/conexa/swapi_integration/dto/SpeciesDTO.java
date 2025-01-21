package com.conexa.swapi_integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Datos de una especie del universo de Star Wars")
public class SpeciesDTO {

    @Schema(description = "Nombre de la especie", example = "Human")
    private String name;

    @Schema(description = "Clasificación de la especie", example = "mammal")
    private String classification;

    @Schema(description = "Designación de la especie", example = "sentient")
    private String designation;

    @Schema(description = "Altura promedio de la especie", example = "180")
    @JsonProperty("average_height")
    private String averageHeight;

    @Schema(description = "Promedio de vida de la especie", example = "120")
    @JsonProperty("average_lifespan")
    private String averageLifespan;

    @Schema(description = "Colores de ojos comunes de la especie", example = "blue, brown, hazel")
    @JsonProperty("eye_colors")
    private String eyeColors;

    @Schema(description = "Colores de pelo comunes de la especie", example = "blond, brown, black")
    @JsonProperty("hair_colors")
    private String hairColors;

    @Schema(description = "Colores de piel comunes de la especie", example = "fair, brown, white")
    @JsonProperty("skin_colors")
    private String skinColors;

    @Schema(description = "Idioma hablado por la especie", example = "Galactic Basic")
    private String language;

    @Schema(description = "URL del planeta natal de la especie", example = "https://swapi.dev/api/planets/9/")
    @JsonProperty("homeworld")
    private String homeWorld;

    @Schema(description = "Lista de URLs de personas pertenecientes a esta especie")
    private List<String> people;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAverageHeight() {
        return averageHeight;
    }

    public void setAverageHeight(String averageHeight) {
        this.averageHeight = averageHeight;
    }

    public String getAverageLifespan() {
        return averageLifespan;
    }

    public void setAverageLifespan(String averageLifespan) {
        this.averageLifespan = averageLifespan;
    }

    public String getEyeColors() {
        return eyeColors;
    }

    public void setEyeColors(String eyeColors) {
        this.eyeColors = eyeColors;
    }

    public String getHairColors() {
        return hairColors;
    }

    public void setHairColors(String hairColors) {
        this.hairColors = hairColors;
    }

    public String getSkinColors() {
        return skinColors;
    }

    public void setSkinColors(String skinColors) {
        this.skinColors = skinColors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHomeWorld() {
        return homeWorld;
    }

    public void setHomeWorld(String homeWorld) {
        this.homeWorld = homeWorld;
    }

    public List<String> getPeople() {
        return people;
    }

    public void setPeople(List<String> people) {
        this.people = people;
    }

}
