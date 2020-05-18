package com.example.fantasyclient;

import java.util.concurrent.CountDownLatch;

//thread to send message
public class TcpSendThread extends SocketThread {
    private final Communicator comms;
    private final String msg;

    public TcpSendThread(CountDownLatch latch, final Communicator comms, final String msg) {
        super(latch);
        this.comms = comms;
        this.msg = msg;
    }

    @Override
    public void run() {
        comms.send_msg(msg);
        super.run();
    }
}