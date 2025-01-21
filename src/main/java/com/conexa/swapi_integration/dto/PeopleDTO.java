package com.conexa.swapi_integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Datos de una persona del universo de Star Wars")
public class PeopleDTO {

    @Schema(description = "Nombre de la persona", example = "Luke Skywalker")
    private String name;

    @Schema(description = "Altura de la persona en centímetros", example = "172")
    private String height;

    @Schema(description = "Masa de la persona en kilogramos", example = "77")
    private String mass;

    @Schema(description = "Color de pelo de la persona", example = "blond")
    @JsonProperty("hair_color")
    private String hairColor;

    @Schema(description = "Color de piel de la persona", example = "fair")
    @JsonProperty("skin_color")
    private String skinColor;

    @Schema(description = "Color de ojos de la persona", example = "blue")
    @JsonProperty("eye_color")
    private String eyeColor;

    @Schema(description = "Año de nacimiento de la persona", example = "19BBY")
    @JsonProperty("birth_year")
    private String birthYear;

    @Schema(description = "Género de la persona", example = "male")
    private String gender;

    @Schema(description = "Fecha de creación del registro", example = "2014-12-09T13:50:51.644000Z")
    private String created;

    @Schema(description = "Fecha de última edición del registro", example = "2014-12-20T21:17:56.891000Z")
    private String edited;

    @Schema(description = "URL del planeta natal de la persona", example = "https://swapi.dev/api/planets/1/")
    @JsonProperty("homeworld")
    private String homeWorld;

    @Schema(description = "URL del recurso de la persona", example = "https://swapi.dev/api/people/1/")
    private String url;

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

    public String getHomeWorld() {
        return homeWorld;
    }

    public void setHomeWorld(String homeWorld) {
        this.homeWorld = homeWorld;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
