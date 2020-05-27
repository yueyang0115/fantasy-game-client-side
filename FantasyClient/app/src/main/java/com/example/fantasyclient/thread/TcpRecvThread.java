package com.example.fantasyclient.thread;

import android.util.Log;

import java.util.concurrent.CountDownLatch;

//thread to receive message
public class TcpRecvThread extends SocketThread {
    private final Communicator comms;
    private StringBuilder stringBuilder;

    public TcpRecvThread(CountDownLatch latch, final Communicator comms, StringBuilder stringBuilder) {
        this.comms = comms;
        this.stringBuilder = stringBuilder;
        this.latch = latch;
    }

    @Override
    public void run() {
        String msg = comms.recvMsg();
        stringBuilder.append(msg);
        Log.d("Tcp receive finished", msg);
        latch.countDown();
    }

    @Override
    public void start () {
        if (thisThread == null) {
            thisThread = new Thread (this);
            thisThread.start();
        }
    }
}
