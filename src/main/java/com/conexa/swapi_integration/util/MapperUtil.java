package com.conexa.swapi_integration.util;

import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.dto.ResultDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapperUtil {

    public static <T> T getObjectFromJson(ResponseEntity<String> responseEntityRaw, Class<T> valueType) throws IOException {
        try {
            ResponseWrapper<T> responseWrapper = ResponseWrapperUtil.fromJson(responseEntityRaw.getBody(), valueType);
                return responseWrapper.getResultDTO().getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> getObjectListFromJson(ResponseEntity<String> responseEntityRaw, Class<T> valueType) throws IOException {
        try {
            ResponseWrapper<T> responseWrapper = ResponseWrapperUtil.fromJson(responseEntityRaw.getBody(), valueType);
                return getObjectList(responseWrapper.getResultDTOList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static  <T>List<T> getObjectList(List<ResultDTO<T>> resultDTOList){
        List<T> filmList = new ArrayList<>();
        resultDTOList.forEach(resultDTO -> {
            filmList.add(resultDTO.getProperties());
        });
        return filmList;
    }
}
