package com.example.fantasyclient;

import android.util.Log;

import com.example.fantasyclient.helper.MessageReceiver;
import com.example.fantasyclient.helper.MessageSender;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.helper.Communicator;

import java.io.IOException;
import java.net.Socket;

/**
 * this mocked service is built to pretend to communicate with server
 */
public class MockedService implements BidirectionalMessageQueue<MessagesC2S,MessagesS2C>{

    private Communicator<MessagesC2S, MessagesS2C> communicator;
    private MessageSender<MessagesC2S> sender = new MessageSender<>();
    private MessageReceiver<MessagesS2C> receiver = new MessageReceiver<>();

    public MockedService() {
        initCommunicator();
    }

    public void initCommunicator(){
        //(new ConnectThread(this)).start();
        try {
            System.out.println("connecting socket");
            Socket socket = new Socket(SocketService.SERVER_IP, SocketService.TCP_PORT);
            communicator = new Communicator<>(socket, new MessagesS2C());
            Log.d("Connection", "Succeed");
        } catch (IOException e) {
            Log.d("Connection", "Error", e);
            return;
        }
        while(communicator==null){}
        new Thread(){
            @Override
            public void run() {
                sender.sendLoop(communicator);
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                receiver.recvLoop(communicator);
            }
        }.start();
    }

    public void enqueue(MessagesC2S m){
        sender.enqueue(m);
    }

    public MessagesS2C dequeue(){
        return receiver.dequeue();
    }

    public void clearQueue(){
        sender.clear();
        receiver.clear();
    }

    public boolean isEmpty(){
        return receiver.isEmpty();
    }
}


