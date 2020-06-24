package com.example.fantasyclient.helper;

import android.util.Log;

import com.example.fantasyclient.thread.Communicator;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageReceiver<T> {
    private LinkedBlockingQueue<T> queue;
    private final String TAG = "MessageReceiver";

    public MessageReceiver() {
        queue = new LinkedBlockingQueue<>();
    }

    public T dequeue(){
        T result = null;
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
            try {
                queue.add((T) c.recvMsg());
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void clear(){
        queue.clear();
    }
}
