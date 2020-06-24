package com.example.fantasyclient;

import android.util.Log;

import com.example.fantasyclient.helper.MessageReceiver;
import com.example.fantasyclient.helper.MessageSender;
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
import com.example.fantasyclient.thread.AcceptThread;
import com.example.fantasyclient.thread.Communicator;

import java.util.ArrayList;
import java.util.List;

public class ServerMock {

    private static final String SUCCESS = "success";
    private static final String INVALID = "invalid";
    private static final String TAG = "ServerMock";
    private MessageSender<MessagesS2C> sender = new MessageSender<>();
    private MessageReceiver<MessagesC2S> receiver = new MessageReceiver<>();
    private Communicator<MessagesS2C, MessagesC2S> communicator;

    public void runServer(){
        (new AcceptThread(communicator)).start();
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
        new Thread() {
            @Override
            public void run() {
                while(communicator==null){}
                if(!receiver.isEmpty()){
                    handleRecvMessage(receiver.dequeue());
                }
            }
        }.start();
    }

    private void handleRecvMessage(MessagesC2S m) {
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
}
