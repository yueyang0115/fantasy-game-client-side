package com.example.fantasyclient;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramSocket;
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
            DatagramSocket udpSocket = new DatagramSocket(SocketService.UDP_PORT);
            socketService.communicator = new Communicator(socket);
            socketService.udpSocket = udpSocket;
            Log.e("Connection:", "Succeed");
        } catch (IOException e) {
            Log.e("Connection:", "Error", e);
        }
        super.run();
    }
}
