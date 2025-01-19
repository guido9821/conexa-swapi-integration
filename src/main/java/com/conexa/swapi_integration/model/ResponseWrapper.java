package com.conexa.swapi_integration.model;

import com.conexa.swapi_integration.dto.ResultDTO;

public class ResponseWrapper <T>{
    private ResultDTO<T> result;
    private String message;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResultDTO<T> getResult() {
        return result;
    }

    public void setResult(ResultDTO<T> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
