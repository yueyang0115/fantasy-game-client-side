package com.example.fantasyclient.helper;

import android.os.Handler;
import android.util.Log;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.thread.Communicator;
import com.example.fantasyclient.thread.TcpSendThread;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageSender {
    LinkedBlockingQueue<MessagesC2S> queue;
    private final String TAG = "MessageSender";

    public MessageSender() {
        queue = new LinkedBlockingQueue<>();
    }

    public void enqueue(final MessagesC2S m){
        queue.add(m);
    }

    public void sendLoop(Communicator c){
        while(true){
            if(!queue.isEmpty()){
                try {
                    c.sendMsg(queue.take());
                } catch (InterruptedException e) {
                    Log.e(TAG,"SendLoop fails");
                }
            }
        }
    }
}
