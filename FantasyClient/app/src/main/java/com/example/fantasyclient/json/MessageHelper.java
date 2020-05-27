package com.example.fantasyclient.json;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MessageHelper {

    public static String serialize(MessagesC2S m) {
        ObjectMapper objectMapper = new ObjectMapper();
        String msg = null;
        try {
            msg = objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            Log.e("MessageHandler", "Serialize");
        }
        return msg;
    }

    public static MessagesS2C deserialize(String string){
        ObjectMapper objectMapper = new ObjectMapper();
        MessagesS2C m = null;
        try {
            m = objectMapper.readValue(string, MessagesS2C.class);
        } catch (IOException e) {
            Log.e("MessageHandler", "Deserialize");
        }
        return m;
    }
}
