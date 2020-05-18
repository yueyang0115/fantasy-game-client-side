package com.example.fantasyclient;

import java.util.concurrent.CountDownLatch;

public class SocketThread  implements Runnable {
    CountDownLatch latch;
    private Thread thisThread;

    public SocketThread(CountDownLatch latch) {
        this.latch = latch;
    }

    public void start() {
        if (thisThread == null) {
            thisThread = new Thread(this);
            thisThread.start();
        }
    }

    @Override
    public void run() {
    }
}
