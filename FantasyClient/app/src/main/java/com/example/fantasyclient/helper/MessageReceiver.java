package com.example.fantasyclient.helper;

import android.util.Log;

import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.thread.Communicator;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageReceiver {
    LinkedBlockingQueue<MessagesS2C> queue;
    private final String TAG = "MessageReceiver";

    public MessageReceiver() {
        queue = new LinkedBlockingQueue<>();
    }

    public MessagesS2C dequeue(){
        MessagesS2C result = null;
        try {
            result = queue.take();
        } catch (InterruptedException e) {
            Log.e(TAG,"Dequeue fails");
            e.printStackTrace();
        }
        return result;
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void recvLoop(Communicator c){
        while(true){
            queue.add(c.recvMsg());
        }
    }
}
