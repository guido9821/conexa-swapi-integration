package com.conexa.swapi_integration.utilTest;


import com.conexa.swapi_integration.dto.FilmDTO;
import com.conexa.swapi_integration.dto.ResultDTO;
import com.conexa.swapi_integration.model.ResponseWrapper;
import com.conexa.swapi_integration.util.MapperUtil;
import com.conexa.swapi_integration.util.ResponseWrapperUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

public class MapperUtilTest {

    @Test
    public void getObjectFromJsonOkTest() throws IOException {
        String jsonResponse = "{\"result\":{\"properties\":{\"title\":\"A New Hope\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        FilmDTO expectedFilm = new FilmDTO();
        expectedFilm.setTitle("A New Hope");

        ResponseWrapper<FilmDTO> responseWrapper = new ResponseWrapper<>();
        ResultDTO<FilmDTO> resultDTO = new ResultDTO<>();
        resultDTO.setProperties(expectedFilm);
        responseWrapper.setResultDTO(resultDTO);

        try (MockedStatic<ResponseWrapperUtil> mockedStatic = mockStatic(ResponseWrapperUtil.class)) {
            mockedStatic.when(() -> ResponseWrapperUtil.fromJson(anyString(), eq(FilmDTO.class)))
                    .thenReturn(responseWrapper);

            FilmDTO actualFilm = MapperUtil.getObjectFromJson(responseEntityRaw, FilmDTO.class);

            assertEquals(expectedFilm.getTitle(), actualFilm.getTitle());
        }


    }

    @Test
    void getObjectFromJsonExceptionTest() {
        String jsonResponse = "{\"result\":{\"properties\":{\"title\":\"A New Hope\"}}}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        try (MockedStatic<ResponseWrapperUtil> mockedStatic = mockStatic(ResponseWrapperUtil.class)) {
            mockedStatic.when(() -> ResponseWrapperUtil.fromJson(anyString(), eq(FilmDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));

            assertThrows(RuntimeException.class, () -> MapperUtil.getObjectFromJson(responseEntityRaw, FilmDTO.class));
        }
    }

    @Test
    void getObjectListFromJsonOkTest() throws IOException {
        String jsonResponse = "{\"result\":[{\"properties\":{\"title\":\"A New Hope\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        FilmDTO film = new FilmDTO();
        film.setTitle("A New Hope");
        ResultDTO<FilmDTO> resultDTO = new ResultDTO<>();
        resultDTO.setProperties(film);

        List<ResultDTO<FilmDTO>> resultDTOList = new ArrayList<>();
        resultDTOList.add(resultDTO);
        ResponseWrapper<FilmDTO> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setResultDTOList(resultDTOList);

        try (MockedStatic<ResponseWrapperUtil> mockedStatic = mockStatic(ResponseWrapperUtil.class)) {
            mockedStatic.when(() -> ResponseWrapperUtil.fromJson(anyString(), eq(FilmDTO.class)))
                    .thenReturn(responseWrapper);

            List<FilmDTO> films = MapperUtil.getObjectListFromJson(responseEntityRaw, FilmDTO.class);

            assertEquals(1, films.size());
            assertEquals("A New Hope", films.get(0).getTitle());
        }
    }

    @Test
    void getObjectListFromJsonExceptionTest() {
        String jsonResponse = "{\"result\":[{\"properties\":{\"title\":\"A New Hope\"}}]}";
        ResponseEntity<String> responseEntityRaw = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        try (MockedStatic<ResponseWrapperUtil> mockedStatic = mockStatic(ResponseWrapperUtil.class)) {
            mockedStatic.when(() -> ResponseWrapperUtil.fromJson(anyString(), eq(FilmDTO.class)))
                    .thenThrow(new IOException("Error parsing JSON"));

            assertThrows(RuntimeException.class, () -> MapperUtil.getObjectListFromJson(responseEntityRaw, FilmDTO.class));
        }
    }

}
