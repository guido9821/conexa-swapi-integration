package com.conexa.swapi_integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Datos de una película de Star Wars") // Descripción general del DTO
public class FilmDTO {

    @Schema(description = "Título de la película", example = "A New Hope")
    private String title;

    @Schema(description = "Número de episodio", example = "4")
    @JsonProperty("episode_id")
    private String episodeId;

    @Schema(description = "Director de la película", example = "George Lucas")
    private String director;

    @Schema(description = "Productor(es) de la película", example = "Gary Kurtz, Rick McCallum")
    private String producer;

    @Schema(description = "Fecha de lanzamiento", example = "1977-05-25")
    @JsonProperty("release_date")
    private String releaseDate;

    @Schema(description = "Texto de apertura de la película", example = "It is a period of civil war...")
    @JsonProperty("opening_crawl")
    private String openingCrawl;

    @Schema(description = "Lista de personajes que aparecen en la película")
    private List<String> characters;

    @Schema(description = "Lista de naves espaciales que aparecen en la película")
    private List<String> starships;

    @Schema(description = "Lista de vehículos que aparecen en la película")
    private List<String> vehicles;

    @Schema(description = "Lista de planetas que aparecen en la película")
    private List<String> planets;

    @Schema(description = "Lista de especies que aparecen en la película")
    private List<String> species;

    public List<String> getPlanets() {
        return planets;
    }

    public void setPlanets(List<String> planets) {
        this.planets = planets;
    }

    public List<String> getSpecies() {
        return species;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public String getOpeningCrawl() {
        return openingCrawl;
    }

    public void setOpeningCrawl(String openingCrawl) {
        this.openingCrawl = openingCrawl;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public List<String> getStarships() {
        return starships;
    }

    public void setStarships(List<String> starships) {
        this.starships = starships;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }
}

