package com.example.fantasyclient.thread;

import android.util.Log;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Communicator<S, R> {
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    private ObjectMapper objectMapper;
    private R recvMsg;
    private static final String TAG = "Communicator";

    public Communicator(Socket in, R r) {
        socket = in;
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,false);
        recvMsg = r;
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
        //MessagesS2C m = new MessagesS2C();
        try {
            recvMsg = (R) objectMapper.readValue(is, recvMsg.getClass());
            Log.d(TAG, "Receive:"+objectMapper.writeValueAsString(recvMsg));
        } catch (IOException e) {
            Log.e(TAG,"Failed to receive data!");
            e.printStackTrace();
        }
        return recvMsg;
    }
}
