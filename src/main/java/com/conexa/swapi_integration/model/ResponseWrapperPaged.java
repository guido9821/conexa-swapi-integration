package com.conexa.swapi_integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapperPaged<T> {

    @Schema(description = "Número total de registros disponibles", example = "82")
    @JsonProperty("total_records")
    private int count;

    @Schema(description = "URL para la siguiente página de resultados", example = "https://swapi.dev/api/people/?page=2&limit=5")
    private String next;

    @Schema(description = "URL para la página anterior de resultados", example = "https://swapi.dev/api/people/?page=1&limit=5")
    private String previous;

    @Schema(description = "Lista de resultados de la página actual")
    private List<T> results;

    public ResponseWrapperPaged() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
