package com.example.fantasyclient.thread;

import android.os.Handler;
import android.util.Log;

import com.example.fantasyclient.json.MessagesC2S;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageSendQueue {
    private Handler handler;
    private LinkedBlockingQueue<MessagesC2S> queue;
    private static final String TAG = "MessageSendQueue";

    public MessageSendQueue() {
        handler = new Handler();
        queue = new LinkedBlockingQueue<>();
    }

    public void enqueue(final MessagesC2S m){
        handler.post(new Runnable() {
            public void run() {
                try {
                    queue.put(m);
                } catch (InterruptedException e) {
                    Log.e(TAG,"Enqueue fails");
                    e.printStackTrace();
                }
            }
        });
    }

    public MessagesC2S dequeue(){
        MessagesC2S m = null;
        if(!queue.isEmpty()) {
            try {
                m = queue.take();
            } catch (InterruptedException e) {
                Log.e(TAG, "Dequeue fails");
            }
        }
        return m;
    }
}
