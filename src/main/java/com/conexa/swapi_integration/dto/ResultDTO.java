package com.conexa.swapi_integration.dto;

public class ResultDTO <T>{
    private T properties;

    public T getProperties() {
        return properties;
    }

    public void setProperties(T properties) {
        this.properties = properties;
    }
}
