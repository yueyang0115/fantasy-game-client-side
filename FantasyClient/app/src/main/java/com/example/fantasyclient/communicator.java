package com.example.fantasyclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class communicator {
    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;
    private static final String TAG = "communicator";

    public communicator(Socket in) {
        socket = in;
        try{
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            Log.e(TAG,"Failed to construct communicator!");
        }

    }

    public void send_msg(String msg) {
        try{
            bw.write(msg);
            bw.flush();
        } catch (IOException e){
            Log.e(TAG,"Failed to send data!");
        }

    }

    public String recv_msg()  {
        String msg="";
        try{
            msg=br.readLine();
        } catch(IOException e){
            Log.e(TAG,"Failed to receive data!");
        }
        return msg;
    }
}
