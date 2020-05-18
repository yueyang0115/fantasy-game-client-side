package com.example.fantasyclient;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

//thread to connect server
public class ConnectThread extends SocketThread {
    private SocketService socketService;

    public ConnectThread(SocketService socketService, CountDownLatch latch) {
        super(latch);
        this.socketService = socketService;
    }

    @Override
    public void run() {
        try {
            System.out.println("connecting socket");
            Socket socket = new Socket(SocketService.SERVER_IP, SocketService.TCP_PORT);
            socketService.comm = new communicator(socket);
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
