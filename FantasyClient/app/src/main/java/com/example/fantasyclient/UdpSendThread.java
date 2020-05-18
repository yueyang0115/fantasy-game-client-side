package com.example.fantasyclient;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

public class UdpSendThread extends SocketThread {
    private final String msg;

    public UdpSendThread(CountDownLatch latch, final String msg) {
        super(latch);
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            DatagramSocket udpSocket = new DatagramSocket(SocketService.UDP_PORT);
            InetAddress serverAddr = InetAddress.getByName(SocketService.SERVER_IP);
            byte[] buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddr, SocketService.UDP_PORT);
            udpSocket.send(packet);
            Log.e("Udp send succeed:", msg);
        } catch (SocketException e) {
            Log.e("Udp:", "Socket Error:", e);
        } catch (IOException e) {
            Log.e("Udp Send:", "IO Error:", e);
        }
        super.run();
    }
}
