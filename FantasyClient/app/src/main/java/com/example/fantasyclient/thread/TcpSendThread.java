package com.example.fantasyclient.thread;

import android.util.Log;

import com.example.fantasyclient.json.MessagesC2S;


//thread to send message
public class TcpSendThread extends SocketThread {
    private final Communicator comms;
    private final MessagesC2S m;

    public TcpSendThread(final Communicator comms, final MessagesC2S m) {
        this.comms = comms;
        this.m = m;
    }

    @Override
    public void run() {
        comms.sendMsg(m);
    }
}
