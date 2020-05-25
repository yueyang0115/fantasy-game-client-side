package com.example.fantasyclient.thread;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
