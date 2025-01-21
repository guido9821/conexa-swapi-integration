package com.conexa.swapi_integration.model;

import com.conexa.swapi_integration.dto.ResultDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapper <T>{

    private List<ResultDTO<T>> resultDTOList;
    private ResultDTO<T> resultDTO;
    private String message;
    private String description;

    public ResultDTO<T> getResultDTO() {
        return resultDTO;
    }

    public void setResultDTO(ResultDTO<T> resultDTO) {
        this.resultDTO = resultDTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResultDTO<T>> getResultDTOList() {
        return resultDTOList;
    }

    public void setResultDTOList(List<ResultDTO<T>> resultDTOList) {
        this.resultDTOList = resultDTOList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
