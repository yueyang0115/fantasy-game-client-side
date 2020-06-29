package com.example.fantasyclient.thread;

import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Communicator<S, R> {
    private OutputStream os;
    private InputStream is;
    private ObjectMapper objectMapper;
    private R receivedMessage;
    private static final String TAG = "Communicator";

    public Communicator(Socket socket, R r) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,false);
        receivedMessage = r;
        try{
            os = socket.getOutputStream();
            is = socket.getInputStream();
        }catch (IOException e){
            Log.e(TAG,"Failed to construct communicator!");
        }
    }

    public void sendMsg(S s) {
        try {
            objectMapper.writeValue(os,s);
            Log.d(TAG, "Send:"+objectMapper.writeValueAsString(s));
        } catch (IOException e) {
            Log.e(TAG,"Failed to send data!");
            e.printStackTrace();
        }
    }

    public R recvMsg() {
        try {
            receivedMessage = (R) objectMapper.readValue(is, receivedMessage.getClass());
            Log.d(TAG, "Receive:"+objectMapper.writeValueAsString(receivedMessage));
        } catch (IOException e) {
            Log.e(TAG,"Failed to receive data!");
            e.printStackTrace();
        }
        return receivedMessage;
    }
}
