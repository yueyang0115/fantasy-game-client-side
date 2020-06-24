package com.example.fantasyclient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.fantasyclient.helper.MessageReceiver;
import com.example.fantasyclient.helper.MessageSender;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.thread.Communicator;
import com.example.fantasyclient.thread.ConnectThread;

import java.net.DatagramSocket;
import java.net.Socket;

/**
 * this service is built to communicate with server
 * it is created in MainActivity when a player click on the button to start
 * then bound to each activity when needed
 */
public class SocketService extends Service {
    public Socket socket;
    public Communicator<MessagesC2S, MessagesS2C> communicator;
    public static String SERVER_IP;
    public DatagramSocket udpSocket;
    public static final int TCP_PORT = 1234;
    public static final int UDP_PORT = 5678;
    public MessageSender<MessagesC2S> sender = new MessageSender<>();
    public MessageReceiver<MessagesS2C> receiver = new MessageReceiver<>();

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
        SERVER_IP = getString(R.string.SERVER_IP);
    }

    public void IsBoundable() {
        Toast.makeText(this, "I bind like butter", Toast.LENGTH_LONG).show();
    }

    public void errorAlert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /*//send message to server
    public void sendTcpMsg(MessagesC2S m) {
        (new TcpSendThread(communicator, m)).start();
    }

    public void sendUdpMsg(String message) {
        (new UdpSendThread(udpSocket,message)).start();
    }

    //receive message from server
    public MessagesS2C recvTcpMsg() {
        List<MessagesS2C> container = new ArrayList<>();
        CountDownLatch recvLatch = new CountDownLatch(1);
        (new TcpRecvThread(recvLatch, communicator, container)).start();
        try{
            recvLatch.await();
        }
        catch (InterruptedException ine){
            System.out.println("Latch Interrupted!");
        }
        return container.get(0);
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "Start command");
        super.onStartCommand(intent, flags, startId);
        initCommunicator();
        return START_STICKY;
    }

    public void initCommunicator(){
        (new ConnectThread(this)).start();
        while(communicator==null){}
        new Thread(){
            @Override
            public void run() {
                sender.sendLoop(communicator);
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                receiver.recvLoop(communicator);
            }
        }.start();
    }

    public void enqueue(MessagesC2S m){
        sender.enqueue(m);
    }

    public MessagesS2C dequeue(){
        return receiver.dequeue();
    }

    public void clearQueue(){
        sender.clear();
        receiver.clear();
    }

    public boolean isEmpty(){
        return receiver.isEmpty();
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


