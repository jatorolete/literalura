// codigo da error al realizar el import del exception
//package com.alura.literalura.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class ConvierteDatos {
//
//    private final ObjectMapper objectMapper = new ObjectMapper ();
//    public <T> T obtenerDatos(String json, Class<T> clase){
//        try {
//            return objectMapper.readValue(json, clase);
//        } catch (JsonprocessingException e){
//            throw new RuntimeException("Error al convertir JSON: "+ e.getMessage());
//        }
//    }
//}

package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConvierteDatos {

    private final ObjectMapper objectMapper =new ObjectMapper();

    public <T> T obtenerDatos(String json, Class<T> clase){
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir JSON: "+ e.getMessage());
        }
    }

}