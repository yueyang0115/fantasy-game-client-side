package com.example.fantasyclient.helper;

import com.example.fantasyclient.MockedServer;
import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.json.LoginRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;

import org.junit.Test;

public class CommunicatorTest {
    @Test
    public void test_Communicator(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                CommunicatorTest.this.client();
            }
        }).start();

        MockedServer server = new MockedServer();
        server.runServer();
        System.out.println("Server initialization successfully");
    }

    private void client()  {
        SocketService socketService = new SocketService();
        socketService.initCommunicator();
        System.out.println("Client connect successfully");

        socketService.enqueue(new MessagesC2S(new LoginRequestMessage()));
    }
}