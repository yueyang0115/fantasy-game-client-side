package com.example.fantasyclient.thread;

import com.example.fantasyclient.helper.MessageSender;
import com.example.fantasyclient.helper.SendTimerHandler;
import com.example.fantasyclient.model.VirtualPosition;

import java.util.concurrent.CountDownLatch;

public class EnqueueLocationThread implements Runnable {
    Thread thisThread;
    boolean ifPause = false;
    MessageSender sender;
    VirtualPosition vPosition;

    public EnqueueLocationThread(VirtualPosition vPosition, MessageSender sender){
        this.sender = sender;
        this.vPosition = vPosition;
    }

    @Override
    public void run() {
        (new SendTimerHandler(vPosition, sender)).handleTask(0,1000);
    }

    synchronized void pause(){
        ifPause = true;
    }

    synchronized void resume(){
        ifPause = false;
        notify();
    }
}
