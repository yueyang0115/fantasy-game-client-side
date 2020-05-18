package com.example.fantasyclient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.DatagramSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * this service is built to communicate with server
 * it is created in MainActivity when a player click on the button to start
 * then bound to each activity when needed
 */
public class SocketService extends Service {
    Socket socket;
    Communicator communicator;
    static final String SERVER_IP = "vcm-13666.vm.duke.edu";
    //static final String SERVER_IP = "vcm-14299.vm.duke.edu";
    DatagramSocket udpSocket;
    static final int TCP_PORT = 1234;
    static final int UDP_PORT = 5678;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        Log.d("Service", "OnBind");
        return myBinder;
    }

    private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public SocketService getService() {
            Log.d("Service", "LocalBinder");
            return SocketService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service", "Create");
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
        (new TcpSendThread(sendLatch, communicator, message)).start();
        latchAwait(sendLatch);
    }

    public void sendUdpMsg(String message) {
        CountDownLatch sendLatch = new CountDownLatch(1);
        (new UdpSendThread(sendLatch, udpSocket,message)).start();
        latchAwait(sendLatch);
    }

    //receive message from server
    public String recvTcpMsg() {
        StringBuilder sb = new StringBuilder();
        CountDownLatch recvLatch = new CountDownLatch(1);
        (new TcpRecvThread(recvLatch, communicator, sb)).start();
        latchAwait(recvLatch);
        return sb.toString();
    }

    private void latchAwait(CountDownLatch latch){
        try {
            latch.await();
        } catch (InterruptedException e) {
            Log.e("Latch", "Error", e);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "Start command");
        super.onStartCommand(intent, flags, startId);
        CountDownLatch connectLatch = new CountDownLatch(1);
        (new ConnectThread(this, connectLatch)).start();
        latchAwait(connectLatch);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("Service destroy", "Error", e);
        }
        socket = null;
    }
}


