package com.conexa.swapi_integration.utilTest;

import com.conexa.swapi_integration.dto.PeopleDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.util.ResponseWrapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ResponseWrapperUtilTest {

    @Mock
    ObjectMapper objectMapper;

    @Test
    void fromJsonSingleResultTest() throws IOException {

        String jsonResponse = "{\"result\":{\"properties\":{\"name\":\"Luke Skywalker\"}}}";

        ResponseWrapper<PeopleDTO> responseWrapper =
                ResponseWrapperUtil.fromJson(jsonResponse, PeopleDTO.class);

        assertNotNull(responseWrapper);
        assertEquals("Luke Skywalker", responseWrapper.getResultDTO().getProperties().getName());
    }

    @Test
    void fromJsonArrayResultTest() throws IOException {

        String jsonResponse = "{\"result\":[{\"properties\":{\"name\":\"Luke Skywalker\"}}]}";

        ResponseWrapper<PeopleDTO> responseWrapper =
                ResponseWrapperUtil.fromJson(jsonResponse, PeopleDTO.class);

        assertNotNull(responseWrapper);
        assertEquals("Luke Skywalker", responseWrapper.getResultDTOList().get(0).getProperties().getName());

    }

    @Test
    void fromJsonExceptionTest() throws JsonProcessingException {

        assertThrows(IOException.class, () ->
                ResponseWrapperUtil.fromJson(null, PeopleDTO.class));
    }

}
