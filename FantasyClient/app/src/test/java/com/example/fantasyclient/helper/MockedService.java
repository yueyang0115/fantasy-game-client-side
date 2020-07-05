package com.example.fantasyclient.helper;

import android.util.Log;

import com.example.fantasyclient.BidirectionalMessageQueue;
import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;

import java.io.IOException;
import java.net.Socket;

/**
 * this mocked service is built to pretend to communicate with server
 */
public class MockedService implements BidirectionalMessageQueue<MessagesC2S,MessagesS2C> {

    private Communicator<MessagesC2S, MessagesS2C> communicator;
    private MessageSender<MessagesC2S> sender = new MessageSender<>();
    private MessageReceiver<MessagesS2C> receiver = new MessageReceiver<>();
    private Socket socket;

    public MockedService() {
        initCommunicator();
    }

    public void initCommunicator(){
        try {
            System.out.println("connecting socket");
            socket = new Socket(SocketService.SERVER_IP, SocketService.TCP_PORT);
            communicator = new Communicator<>(socket, new MessagesS2C());
            Log.d("Connection", "Succeed");
        } catch (IOException e) {
            Log.d("Connection", "Error", e);
            return;
        }

        new Thread(){
            @Override
            public void run() {
                while(communicator==null){}
                sender.sendLoop(communicator);
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                while(communicator==null){}
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

    public void closeQueue(){
        sender.close();
        receiver.close();
    }

    public boolean isEmpty(){
        return receiver.isEmpty();
    }

    protected void finalize(){
        try{
            sender.close();
            receiver.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not close socket");
            System.exit(-1);
        }
    }
}


