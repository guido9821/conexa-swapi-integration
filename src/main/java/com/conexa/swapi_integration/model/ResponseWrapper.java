package com.conexa.swapi_integration.model;

import com.conexa.swapi_integration.dto.ResultDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapper <T>{

    private List<ResultDTO<T>> resultDTOList;
    private ResultDTO<T> resultDTO;
    private String message;
    private String description;

    public static <T> ResponseWrapper<T> fromJson(String json, Class<T> resultType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        wrapper.setMessage(rootNode.path("message").asText());
        wrapper.setDescription(rootNode.path("description").asText());
        JsonNode resultNode = rootNode.path("result");
        if (resultNode.isArray()) {
            List<ResultDTO<T>> results = objectMapper.readValue(
                    resultNode.traverse(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, objectMapper.getTypeFactory().constructParametricType(ResultDTO.class, resultType))
            );
            wrapper.setResultDTOList(results);
        } else if (resultNode.isObject()) {
            ResultDTO<T> result = objectMapper.readValue(
                    resultNode.traverse(),
                    objectMapper.getTypeFactory().constructParametricType(ResultDTO.class, resultType)
            );
            wrapper.setResultDTO(result);
        }

        return wrapper;
    }

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
