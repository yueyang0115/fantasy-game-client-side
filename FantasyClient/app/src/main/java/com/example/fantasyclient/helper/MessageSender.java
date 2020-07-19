package com.example.fantasyclient.helper;

import android.util.Log;

public class MessageSender<T> extends MessageQueue<T>{

    public MessageSender() {
        super();
        TAG = "MessageSender";
    }

    public void enqueue(final T t){
        queue.add(t);
    }

    public void sendLoop(Communicator c){
        while(!ifClosed){
            try {
                c.sendMsg(queue.take());//queue.take() will
            } catch (InterruptedException e) {
                Log.e(TAG,"SendLoop fails");
            }
        }
    }
}
