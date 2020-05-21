package com.example.fantasyclient;

public class SocketThread  implements Runnable {
    private Thread thisThread;

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
