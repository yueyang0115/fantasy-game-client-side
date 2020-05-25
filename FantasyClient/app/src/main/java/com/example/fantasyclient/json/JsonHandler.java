package com.example.fantasyclient.json;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHandler {
    private ObjectMapper objectMapper;
    private MessagesC2S target;

    public JsonHandler(MessagesC2S target) {
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

    public MessagesC2S deserialize(MessagesC2S msg, String string){
        try {
            target = (MessagesC2S) objectMapper.readValue(string, MessagesC2S.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }
}
