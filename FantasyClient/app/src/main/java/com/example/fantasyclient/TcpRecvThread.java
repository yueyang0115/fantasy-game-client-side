package com.example.fantasyclient;

import android.util.Log;

import java.util.concurrent.CountDownLatch;

//thread to receive message
public class TcpRecvThread extends SocketThread {
    private final Communicator comms;
    private StringBuilder stringBuilder;

    public TcpRecvThread(CountDownLatch latch, final Communicator comms, StringBuilder stringBuilder) {
        super(latch);
        this.comms = comms;
        this.stringBuilder = stringBuilder;
    }

    @Override
    public void run() {
        String msg = comms.recv_msg();
        stringBuilder.append(msg);
        Log.d("Tcp receive finished", msg);
        super.run();
    }
}
