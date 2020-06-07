package com.example.fantasyclient.thread;

import java.util.concurrent.CountDownLatch;

public class SocketThread  implements Runnable {
    Thread thisThread;
    CountDownLatch latch;

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
