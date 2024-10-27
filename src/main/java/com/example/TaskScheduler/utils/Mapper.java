package com.example.TaskScheduler.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class Mapper<T> {

    @Autowired
    private ObjectMapper objMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(Mapper.class);

    public T stringToObj(String json, Class <T> clazz) {
        T response = null;
        try {
            response = objMapper.readValue(json, clazz);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error Parsing: {}", ex.getMessage());
        }
        return response;
    }
    public String convertFileToString(String path) {
        String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException ex) {
            LOGGER.error("Error reading json file: {}", ex.getMessage());
        }
        return json;
    }
    public String objToJson(Object object) {
        String json = "";
        try {
            json = objMapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error Parsing: {}", ex.getMessage());
        }
        return json;
    }
}
