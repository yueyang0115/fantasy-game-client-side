package com.example.fantasyclient.thread;

import com.example.fantasyclient.ServerMock;
import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.json.SignUpRequestMessage;
import com.example.fantasyclient.json.SignUpResultMessage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicatorTest {
    @Test
    public void test_Communicator(){
        ServerMock server = new ServerMock();
        server.runServer();
        System.out.println("Server initialization successfully");

        new Thread(new Runnable() {
            @Override
            public void run() {
                CommunicatorTest.this.client();
            }
        }).start();

        while(true){}
    }

    public void client()  {
        SocketService socketService = new SocketService();
        socketService.initCommunicator();
        System.out.println("Client connect successfully");
    }
}