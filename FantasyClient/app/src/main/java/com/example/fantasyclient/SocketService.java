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

    public void errorAlert(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //send message to server
    public void send_msg(String message) throws IOException {
        CountDownLatch sendLatch = new CountDownLatch(1);
        SendThread sendThread = new SendThread(sendLatch, comm, message);
        sendThread.start();
        try{
            sendLatch.await();
        }
        catch (InterruptedException ine){
            System.out.println("Latch Interrupted!");
        }
        System.out.println(message);
        System.out.println("\nSend succeed!\n");
    }

    //receive message from server
    public String recv_msg() throws IOException {
        StringBuilder sb = new StringBuilder();
        CountDownLatch recvLatch = new CountDownLatch(1);
        RecvThread recvThread = new RecvThread(recvLatch, comm, sb);
        recvThread.start();
        try{
            recvLatch.await();
        }
        catch (InterruptedException ine){
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
        ConnectThread connectThread = new ConnectThread(connectLatch);
        connectThread.start();
        try{
            connectLatch.await();
        }
        catch (InterruptedException ine){
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

    //thread to connect server
    public class ConnectThread implements Runnable {
        private CountDownLatch latch;
        private Thread thisThread;

        public ConnectThread(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println("connecting socket");
                Socket socket = new Socket("vcm-14299.vm.duke.edu", 1234);
                comm = new communicator(socket);
                latch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void start () {
            if (thisThread == null) {
                thisThread = new Thread (this);
                thisThread.start();
            }
        }
    }

    //thread to receive message
    public class RecvThread implements Runnable {
        private CountDownLatch latch;
        private final communicator comms;
        private StringBuilder stringBuilder;
        private Thread thisThread;

        public RecvThread(CountDownLatch latch, final communicator comms, StringBuilder stringBuilder) {
            this.latch = latch;
            this.comms = comms;
            this.stringBuilder = stringBuilder;
        }

        @Override
        public void run() {
            try {
                String msg = comms.recv_msg();
                stringBuilder.append(msg);
                latch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void start () {
            if (thisThread == null) {
                thisThread = new Thread (this);
                thisThread.start();
            }
        }
    }

    //thread to send message
    public class SendThread implements Runnable {
        private CountDownLatch latch;
        private final communicator comms;
        private final String msg;
        private Thread thisThread;

        public SendThread(CountDownLatch latch, final communicator comms, final String msg) {
            this.latch = latch;
            this.comms = comms;
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                comms.send_msg(msg);
                latch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void start () {
            if (thisThread == null) {
                thisThread = new Thread (this);
                thisThread.start();
            }
        }
    }
}


