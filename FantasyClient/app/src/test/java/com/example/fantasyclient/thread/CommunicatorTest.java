package com.example.fantasyclient.thread;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.json.SignUpRequestMessage;
import com.example.fantasyclient.json.SignUpResultMessage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicatorTest {
    @Test
    public void test_Communicator(){
        try{
            ObjectMapper om = new ObjectMapper();
            om.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            om.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);

            ServerSocket ss = new ServerSocket(1111);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CommunicatorTest.this.client();
                }
            }).start();

            Socket sk = ss.accept();
            Communicator ct = new Communicator(sk);

            MessagesC2S msg = new MessagesC2S();
            SignUpRequestMessage signup_msg = new SignUpRequestMessage("js895", "1111");
            msg.setSignUpRequestMessage(signup_msg);

            ct.sendMsg(msg);
            System.out.println("Server first send: " + om.writeValueAsString(msg));

            MessagesS2C mtg = ct.recvMsg();
            System.out.println("Server second recv: " + om.writeValueAsString(mtg));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void client()  {
        {
            try {
                Socket socket = new Socket("0.0.0.0", 1111);
                ObjectMapper om = new ObjectMapper();
                om.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
                om.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);

                MessagesC2S msg = om.readValue(socket.getInputStream(), MessagesC2S.class);
                System.out.println("Client first receive: " + om.writeValueAsString(msg));

                MessagesS2C mtg = new MessagesS2C();
                SignUpResultMessage signup_mtg = new SignUpResultMessage();
                mtg.setSignUpResultMessage(signup_mtg);
                om.writeValue(socket.getOutputStream(), mtg);
                System.out.println("Client second send: " + om.writeValueAsString(mtg));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}