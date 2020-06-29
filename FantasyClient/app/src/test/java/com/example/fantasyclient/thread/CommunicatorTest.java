package com.example.fantasyclient.thread;

import com.example.fantasyclient.MockedServer;
import com.example.fantasyclient.SocketService;

import org.junit.Test;

public class CommunicatorTest {
    @Test
    public void test_Communicator(){
        MockedServer server = new MockedServer();
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