package com.example.fantasyclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the mock server class.
 * */
public class ServerMock {
    private int port; // server's port number
    private ServerSocket listenSocket; // server's own socket (listen on)

    public ServerMock() throws IOException {
        port = 12345;
        listenSocket = new ServerSocket(port);
    }


    private void acceptPlayer() throws IOException {
        Socket sockToClient = listenSocket.accept();
        Communicator newCommunicator = new Communicator(sockToClient); // create a new communicator
        System.out.println("Accepted Host!");
        System.out.println("Handshake Host!");
        String msg = newCommunicator.recv_msg();
        newCommunicator.send_msg(msg);
    }
}
