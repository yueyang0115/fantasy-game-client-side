package com.example.fantasyclient.thread;

import com.example.fantasyclient.json.MessagesS2C;

import java.util.List;
import java.util.concurrent.CountDownLatch;

//thread to receive message
public class TcpRecvThread extends SocketThread {
    private final Communicator comms;
    private List<MessagesS2C> container;

    public TcpRecvThread(CountDownLatch latch, final Communicator comms, List<MessagesS2C> container) {
        this.comms = comms;
        this.container = container;
        this.latch = latch;
    }

    @Override
    public void run() {
        container.add(comms.recvMsg());
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
