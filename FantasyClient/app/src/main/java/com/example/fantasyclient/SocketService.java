package com.example.fantasyclient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * this service is built to communicate with server
 * it is created in MainActivity when a player click on the button to start
 * then bound to each activity when needed
 */
public class SocketService extends Service {
    Socket socket;
    communicator comm;
    static final String SERVER_IP = "vcm-14299.vm.duke.edu";
    static final int TCP_PORT = 1234;
    static final int UDP_PORT = 5678;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("I am in Ibinder onBind method");
        return myBinder;
    }

    private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public SocketService getService() {
            System.out.println("I am in Localbinder ");
            return SocketService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("I am in on create");
    }

    public void IsBoundable() {
        Toast.makeText(this, "I bind like butter", Toast.LENGTH_LONG).show();
    }

    public void errorAlert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //send message to server
    public void sendTcpMsg(String message) {
        CountDownLatch sendLatch = new CountDownLatch(1);
        TcpSendThread tcpSendThread = new TcpSendThread(sendLatch, comm, message);
        tcpSendThread.start();
        try {
            sendLatch.await();
        } catch (InterruptedException ine) {
            System.out.println("Latch Interrupted!");
        }
        System.out.println(message);
        System.out.println("\nSend succeed!\n");
    }

    public void sendUdpMsg(String message) {
        CountDownLatch sendLatch = new CountDownLatch(1);
        UdpSendThread udpSendThread = new UdpSendThread(sendLatch, message);
        udpSendThread.start();
        try {
            sendLatch.await();
        } catch (InterruptedException ine) {
            System.out.println("Latch Interrupted!");
        }
        System.out.println(message);
        System.out.println("\nSend succeed!\n");
    }

    //receive message from server
    public String recvTcpMsg() {
        StringBuilder sb = new StringBuilder();
        CountDownLatch recvLatch = new CountDownLatch(1);
        TcpRecvThread tcpRecvThread = new TcpRecvThread(recvLatch, comm, sb);
        tcpRecvThread.start();
        try {
            recvLatch.await();
        } catch (InterruptedException ine) {
            System.out.println("Latch Interrupted!");
        }
        System.out.println("\nReceive succeed!\n");
        System.out.println(sb.toString());
        return sb.toString();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("I am in on start");
        super.onStartCommand(intent, flags, startId);
        //  Toast.makeText(this,"Service created ...", Toast.LENGTH_LONG).show();
        CountDownLatch connectLatch = new CountDownLatch(1);
        ConnectThread connectThread = new ConnectThread(this, connectLatch);
        connectThread.start();
        try {
            connectLatch.await();
        } catch (InterruptedException ine) {
            System.out.println("Latch Interrupted!");
        }
        System.out.println("\nConnection succeed!\n");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        socket = null;
    }
}


