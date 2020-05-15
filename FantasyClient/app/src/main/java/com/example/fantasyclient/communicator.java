package com.example.fantasyclient;

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

    public communicator(Socket in) {
        socket = in;
    }

    public void send_msg(String msg) throws IOException {
        OutputStream os = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        bw.write(msg);
        bw.flush();
    }

    public String recv_msg() throws IOException {
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String msg=br.readLine();
        return msg;
    }
}
