package com.example.fantasyclient;

import java.util.concurrent.CountDownLatch;

//thread to receive message
public class TcpRecvThread extends SocketThread {
    private final communicator comms;
    private StringBuilder stringBuilder;

    public TcpRecvThread(CountDownLatch latch, final communicator comms, StringBuilder stringBuilder) {
        super(latch);
        this.comms = comms;
        this.stringBuilder = stringBuilder;
    }

    @Override
    public void run() {
        String msg = comms.recv_msg();
        stringBuilder.append(msg);
        latch.countDown();
    }
}
