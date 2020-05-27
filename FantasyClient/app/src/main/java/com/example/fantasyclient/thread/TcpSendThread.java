package com.example.fantasyclient.thread;

import android.util.Log;


//thread to send message
public class TcpSendThread extends SocketThread {
    private final Communicator comms;
    private final String msg;

    public TcpSendThread(final Communicator comms, final String msg) {
        this.comms = comms;
        this.msg = msg;
    }

    @Override
    public void run() {
        comms.sendMsg(msg+"\n");
        Log.d("Tcp send finished", msg);
    }
}
