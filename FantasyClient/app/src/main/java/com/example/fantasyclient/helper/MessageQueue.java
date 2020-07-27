package com.example.fantasyclient.helper;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class MessageQueue<T> {

    LinkedBlockingQueue<T> queue;
    protected String TAG;
    boolean ifClosed = false;

    MessageQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void clear(){
        queue.clear();
    }

    public void close(){
        ifClosed = true;
    }
}
