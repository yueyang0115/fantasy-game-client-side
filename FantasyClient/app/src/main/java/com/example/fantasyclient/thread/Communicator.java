package com.example.fantasyclient.thread;

import android.util.Log;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        objectMapper = new ObjectMapper();
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
        } catch (IOException e) {
            Log.e(TAG,"Failed to send data!");
        }
    }

    public MessagesS2C recvMsg()  {
        MessagesS2C m = null;
        try {
            m = objectMapper.readValue(is, MessagesS2C.class);
        } catch (IOException e) {
            Log.e(TAG,"Failed to receive data!");
        }
        return m;
    }
}
