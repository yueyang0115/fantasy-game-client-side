package com.example.fantasyclient.helper;

import android.util.Log;

import com.example.fantasyclient.thread.Communicator;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageSender<T> {
    private LinkedBlockingQueue<T> queue;
    private final String TAG = "MessageSender";

    public MessageSender() {
        queue = new LinkedBlockingQueue<>();
    }

    public void enqueue(final T t){
        queue.add(t);
    }

    public void sendLoop(Communicator c){
        while(true){
            try {
                c.sendMsg(queue.take());//queue.take() will
            } catch (InterruptedException e) {
                Log.e(TAG,"SendLoop fails");
                break;
            }
        }
    }

    public void clear(){
        queue.clear();
    }
}
