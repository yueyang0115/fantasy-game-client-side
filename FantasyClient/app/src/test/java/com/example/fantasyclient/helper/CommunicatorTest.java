package com.example.fantasyclient.helper;

import com.example.fantasyclient.json.LoginRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommunicatorTest {
    @Test
    public void test_Communicator(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                runClient();
            }
        }).start();

        MockedServer server = new MockedServer();
        server.runServer();
        System.out.println("Server initialization successfully");
        server.handleRecvMessage(server.dequeue());
        System.out.println("Server receive successfully");
        server.clearQueue();
        assertTrue(server.isEmpty());
        server.closeQueue();
    }

    private void runClient()  {
        MockedService service = new MockedService();
        System.out.println("Client connect successfully");
        service.enqueue(new MessagesC2S(new LoginRequestMessage()));
        System.out.println("Client send successfully");
        service.closeQueue();
    }
}