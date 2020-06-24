package com.example.fantasyclient;

import com.example.fantasyclient.helper.MessageReceiver;
import com.example.fantasyclient.helper.MessageSender;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.thread.AcceptThread;
import com.example.fantasyclient.thread.Communicator;

import org.junit.Test;

public class ServerMock {

    private MessageSender sender = new MessageSender();
    private MessageReceiver receiver = new MessageReceiver();
    private Communicator<MessagesS2C, MessagesC2S> communicator;

    @Test
    public void runServer(){
        (new AcceptThread(communicator)).start();
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
}
