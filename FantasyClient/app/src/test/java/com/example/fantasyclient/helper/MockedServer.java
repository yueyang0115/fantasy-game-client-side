package com.example.fantasyclient.helper;

import android.util.Log;

import com.example.fantasyclient.BidirectionalMessageQueue;
import com.example.fantasyclient.json.AttributeRequestMessage;
import com.example.fantasyclient.json.AttributeResultMessage;
import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BattleResultMessage;
import com.example.fantasyclient.json.BuildingRequestMessage;
import com.example.fantasyclient.json.BuildingResultMessage;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.LoginRequestMessage;
import com.example.fantasyclient.json.LoginResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.json.PositionRequestMessage;
import com.example.fantasyclient.json.PositionResultMessage;
import com.example.fantasyclient.json.ShopRequestMessage;
import com.example.fantasyclient.json.ShopResultMessage;
import com.example.fantasyclient.json.SignUpRequestMessage;
import com.example.fantasyclient.json.SignUpResultMessage;
import com.example.fantasyclient.model.Soldier;
import com.example.fantasyclient.model.Territory;
import com.example.fantasyclient.model.WorldCoord;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.example.fantasyclient.SocketService.TCP_PORT;

public class MockedServer implements BidirectionalMessageQueue<MessagesS2C, MessagesC2S> {

    private static final String SUCCESS = "success";
    private static final String INVALID = "invalid";
    private static final String TAG = "ServerMock";
    private MessageSender<MessagesS2C> sender = new MessageSender<>();
    private MessageReceiver<MessagesC2S> receiver = new MessageReceiver<>();
    private Communicator<MessagesS2C, MessagesC2S> communicator;
    private ServerSocket serverSocket;
    private Socket socket;

    public void runServer(){
        initCommunicator();
    }

    public void handleRecvMessage(MessagesC2S m) {
        if (m == null) {
            Log.e(TAG, "HandleRecvMessage: Invalid result received");
        } else {
            if (m.getLoginRequestMessage() != null) {
                checkLoginRequest(m.getLoginRequestMessage());
            }
            if (m.getSignUpRequestMessage() != null) {
                checkSignUpRequest(m.getSignUpRequestMessage());
            }
            if (m.getPositionRequestMessage() != null) {
                checkPositionRequest(m.getPositionRequestMessage());
            }
            if (m.getBattleRequestMessage() != null) {
                checkBattleRequest(m.getBattleRequestMessage());
            }
            if (m.getAttributeRequestMessage() != null) {
                checkAttributeRequest(m.getAttributeRequestMessage());
            }
            if (m.getShopRequestMessage() != null) {
                checkShopRequest(m.getShopRequestMessage());
            }
            if (m.getInventoryRequestMessage() != null) {
                checkInventoryRequest(m.getInventoryRequestMessage());
            }
            if (m.getBuildingRequestMessage() != null) {
                checkBuildingRequest(m.getBuildingRequestMessage());
            }
        }
    }

    private void checkLoginRequest(LoginRequestMessage m){
        sender.enqueue(new MessagesS2C(new LoginResultMessage(1,1,null,SUCCESS)));
    }
    
    private void checkSignUpRequest(SignUpRequestMessage m){
        sender.enqueue(new MessagesS2C(new SignUpResultMessage(null, SUCCESS)));
    }
    
    private void checkPositionRequest(PositionRequestMessage m){
        List<Territory> territories = new ArrayList<>();
        for(WorldCoord c : m.getCoords()){
            territories.add(new Territory(c,0,"grass"));
        }
        sender.enqueue(new MessagesS2C(new PositionResultMessage(territories)));
    }
    
    private void checkBattleRequest(BattleRequestMessage m){
        sender.enqueue(new MessagesS2C(new BattleResultMessage("escaped")));
    }
    
    private void checkAttributeRequest(AttributeRequestMessage m){
        sender.enqueue(new MessagesS2C(new AttributeResultMessage(new ArrayList<Soldier>())));
    }
    
    private void checkShopRequest(ShopRequestMessage m){
        sender.enqueue(new MessagesS2C(new ShopResultMessage(INVALID)));
    }
    
    private void checkInventoryRequest(InventoryRequestMessage m){
        sender.enqueue(new MessagesS2C(new InventoryResultMessage(INVALID)));
    }
    
    private void checkBuildingRequest(BuildingRequestMessage m){
        sender.enqueue(new MessagesS2C(new BuildingResultMessage(INVALID)));
    }

    @Override
    public void initCommunicator() {
        try {
            System.out.println("accepting socket");
            serverSocket = new ServerSocket(TCP_PORT);
            socket = serverSocket.accept();
            communicator = new Communicator<>(socket, new MessagesC2S());
            Log.d("Connection", "Succeed");
        } catch (IOException e) {
            Log.d("Connection", "Error", e);
            return;
        }
        new Thread(){
            @Override
            public void run() {
                while(communicator==null){}
                sender.sendLoop(communicator);
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                while(communicator==null){}
                receiver.recvLoop(communicator);
            }
        }.start();
    }

    @Override
    public void enqueue(MessagesS2C messagesS2C) {
        sender.enqueue(messagesS2C);
    }

    @Override
    public MessagesC2S dequeue() {
        return receiver.dequeue();
    }

    @Override
    public void clearQueue() {
        sender.clear();
        receiver.clear();
    }

    public void closeQueue(){
        sender.close();
        receiver.close();
    }

    @Override
    public boolean isEmpty() {
        return receiver.isEmpty();
    }

    protected void finalize(){
        try{
            sender.close();
            receiver.close();
            serverSocket.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not close socket");
            System.exit(-1);
        }
    }
}
