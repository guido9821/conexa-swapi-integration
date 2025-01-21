package com.conexa.swapi_integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Objeto que encapsula propiedades y una descripción (ej. para resultados de búsqueda)")
public class ResultDTO<T> {

    @Schema(description = "Propiedades del resultado", example = "{\"name\": \"Luke Skywalker\", \"height\": \"172\", ...}") // Example as JSON string
    private T properties;

    @Schema(description = "Descripción del resultado", example = "Información sobre Luke Skywalker")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getProperties() {
        return properties;
    }

    public void setProperties(T properties) {
        this.properties = properties;
    }
}
