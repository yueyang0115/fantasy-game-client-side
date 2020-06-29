package com.example.fantasyclient.thread;

import android.util.Log;

import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.json.MessagesS2C;

import java.io.IOException;
import java.net.Socket;

//thread to connect server
public class ConnectThread extends SocketThread {
    private SocketService socketService;

    public ConnectThread(SocketService socketService) {
        this.socketService = socketService;
    }

    @Override
    public void run() {
        try {
            System.out.println("connecting socket");
            Socket socket = new Socket(SocketService.SERVER_IP, SocketService.TCP_PORT);
            /*DatagramSocket udpSocket = new DatagramSocket(SocketService.UDP_PORT);
            socketService.udpSocket = udpSocket;*/
            socketService.communicator = new Communicator<>(socket, new MessagesS2C());
            Log.d("Connection", "Succeed");
        } catch (IOException e) {
            Log.d("Connection", "Error", e);
        }
    }
}
