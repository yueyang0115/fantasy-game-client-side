package com.example.fantasyclient.json;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHandler {
    private ObjectMapper objectMapper;
    private JsonBase target;

    public JsonHandler(JsonBase target) {
        objectMapper = new ObjectMapper();
        this.target = target;
    }

    public String serialize() {
        String msg = "";
        try {
            msg = objectMapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            Log.e("JsonHandler", "ObjectMapper");
        }
        return msg;
    }

    public JsonBase deserialize(JsonBase targetType, String string){
        try {
            target = (JsonBase) objectMapper.readValue(string, targetType.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }
}
