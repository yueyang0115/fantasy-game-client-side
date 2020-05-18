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
    private final DatagramSocket udpSocket;


    public UdpSendThread(CountDownLatch latch, DatagramSocket udpSocket,final String msg) {
        super(latch);
        this.msg = msg;
        this.udpSocket = udpSocket;
    }

    @Override
    public void run() {
        try {
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
