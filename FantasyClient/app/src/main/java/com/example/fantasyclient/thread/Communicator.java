package com.example.fantasyclient.thread;

import android.util.Log;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Communicator {
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    private ObjectMapper objectMapper;
    private static final String TAG = "Communicator";

    public Communicator(Socket in) {
        socket = in;
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,false);

        try{
            os = socket.getOutputStream();
            is = socket.getInputStream();
        }catch (IOException e){
            Log.e(TAG,"Failed to construct communicator!");
        }

    }

    public void sendMsg(MessagesC2S m) {
        try {
            objectMapper.writeValue(os,m);
            Log.d(TAG, "Send:"+objectMapper.writeValueAsString(m));
        } catch (IOException e) {
            Log.e(TAG,"Failed to send data!");
            e.printStackTrace();
        }
    }

    public MessagesS2C recvMsg()  {
        MessagesS2C m = new MessagesS2C();
        try {
            m = objectMapper.readValue(is, MessagesS2C.class);
            Log.d(TAG, "Receive:"+objectMapper.writeValueAsString(m));
        } catch (IOException e) {
            Log.e(TAG,"Failed to receive data!");
        }
        return m;
    }
}
