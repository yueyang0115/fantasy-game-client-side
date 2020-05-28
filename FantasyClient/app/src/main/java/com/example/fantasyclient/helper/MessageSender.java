package com.example.fantasyclient.helper;

import android.os.Handler;
import android.util.Log;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.thread.Communicator;
import com.example.fantasyclient.thread.TcpSendThread;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageSender {
    final Handler handler;
    LinkedBlockingQueue<MessagesC2S> queue;
    private final String TAG = "MessageSender";

    public MessageSender() {
        handler = new Handler();
        queue = new LinkedBlockingQueue<>();
    }

    public void enqueue(final MessagesC2S m){
        handler.post(new Runnable() {
            @Override
            public void run() {
                queue.add(m);
            }
        });
    }

    public void sendLoop(Communicator c){
        while(true){
            if(!queue.isEmpty()){
                try {
                    (new TcpSendThread(c,queue.take())).start();
                } catch (InterruptedException e) {
                    Log.e(TAG,"SendLoop fails");
                }
            }
        }
    }
}
