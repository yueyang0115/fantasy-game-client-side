package com.example.fantasyclient.helper;

import android.util.Log;

public class MessageReceiver<T> extends MessageQueue<T>{

    public MessageReceiver() {
        super();
        TAG = "MessageReceiver";
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

    public void recvLoop(Communicator c){
        while(!ifClosed){
            try {
                queue.add((T) c.recvMsg());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
