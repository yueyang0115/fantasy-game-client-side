package com.example.fantasyclient;

import android.util.Log;

//thread to receive message
public class TcpRecvThread extends SocketThread {
    private final Communicator comms;
    private StringBuilder stringBuilder;

    public TcpRecvThread(final Communicator comms, StringBuilder stringBuilder) {
        this.comms = comms;
        this.stringBuilder = stringBuilder;
    }

    @Override
    public void run() {
        String msg = comms.recv_msg();
        stringBuilder.append(msg);
        Log.d("Tcp receive finished", msg);
    }

    @Override
    public void start () {
        if (thisThread == null) {
            thisThread = new Thread (this);
            thisThread.start();
        }
    }
}
