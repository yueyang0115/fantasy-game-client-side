package com.example.fantasyclient.thread;

import android.util.Log;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;

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
            communicator = new Communicator<MessagesS2C,MessagesC2S>(socket, new MessagesC2S());
            Log.d("Connection", "Succeed");
        } catch (IOException e) {
            Log.d("Connection", "Error", e);
        }
    }
}
