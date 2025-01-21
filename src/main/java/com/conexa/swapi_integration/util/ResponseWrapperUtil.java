package com.conexa.swapi_integration.util;

import com.conexa.swapi_integration.dto.ResultDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ResponseWrapperUtil {

    public static <T> ResponseWrapper<T> fromJson(String json, Class<T> resultType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
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
        }catch (IOException ex){
            throw new IOException("Error al procesar el resultado");
        }
        return wrapper;
    }

}
