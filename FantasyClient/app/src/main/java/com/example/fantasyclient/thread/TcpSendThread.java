package com.example.fantasyclient.thread;

import com.example.fantasyclient.json.MessagesC2S;


//thread to send message
public class TcpSendThread extends SocketThread {
    private final Communicator comms;
    private MessagesC2S m;

    public TcpSendThread(Communicator comms) {
        this.comms = comms;
    }

    public TcpSendThread(final Communicator comms, final MessagesC2S m) {
        this.comms = comms;
        this.m = m;
    }

    public void setMessage(final MessagesC2S m){
        this.m = m;
    }

    @Override
    public void run() {
        comms.sendMsg(m);
    }
}
