package com.example.fantasyclient.thread;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.fantasyclient.SocketService.TCP_PORT;

//thread to connect server
public class AcceptThread extends SocketThread {
    private Communicator communicator;

    public AcceptThread(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void run() {
        try {
            System.out.println("accepting socket");
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            Socket socket = serverSocket.accept();
            communicator = new Communicator(socket);
            Log.d("Connection", "Succeed");
        } catch (IOException e) {
            Log.d("Connection", "Error", e);
        }
    }
}
