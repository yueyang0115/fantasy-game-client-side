package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.helper.MessageReceiver;
import com.example.fantasyclient.helper.MessageSender;
import com.example.fantasyclient.json.AttributeRequestMessage;
import com.example.fantasyclient.json.AttributeResultMessage;
import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BattleResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.model.Monster;
import com.example.fantasyclient.model.Soldier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * This is a template activity related to user accounts
 */
@SuppressLint("Registered")
public class BattleActivity extends BaseActivity{
    Button attackBtn, escapeBtn;
    ImageView soldierImg, monsterImg;
    TextView soldierAtk, soldierHp, monsterAtk, monsterHp;
    CountDownLatch latch = new CountDownLatch(1);
    List<Soldier> soldiers = new ArrayList<>();
    List<Monster> monsters = new ArrayList<>();
    int terrID, monsterID, soldierID = 0;
    boolean ifPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        findView();
        doBindService();
        Intent intent = getIntent();
        terrID = intent.getIntExtra("territoryID",0);
        monsterID = intent.getIntExtra("monsterID",0);

        new Thread(){
            @Override
            public void run() {
                while (socketService==null){}
                socketService.enqueue(new MessagesC2S(new AttributeRequestMessage("battle")));
                handleRecvMessage(socketService.dequeue());
                latch.countDown();
            }
        }.start();

        attackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage(terrID,monsterID,soldierID,"attack")));
                handleRecvMessage(socketService.dequeue());
            }
        });

        escapeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage(terrID,monsterID,soldierID,"escape")));
                handleRecvMessage(socketService.dequeue());
            }
        });
    }

    /**
     * find required common views which may be overrode
     */
    @Override
    protected void findView(){
        attackBtn = findViewById(R.id.attackBtn);
        escapeBtn = findViewById(R.id.escapeBtn);
        soldierImg = findViewById(R.id.soldierImg);
        monsterImg = findViewById(R.id.monsterImg);
        soldierImg.setImageResource(R.drawable.pickachu_back);
        monsterImg.setImageResource(R.drawable.wolf_battle);
        soldierHp = findViewById(R.id.soldierHp);
        soldierAtk = findViewById(R.id.soldierAtk);
        monsterHp = findViewById(R.id.monsterHp);
        monsterAtk = findViewById(R.id.monsterAtk);
    }

    /**
     * This method is called after a MessageS2C with BattleResultMessage is received from server
     * UI and cached map will be updated based on the received message
     * @param m: received BattleResultMessage
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void checkBattleResult(final BattleResultMessage m){
        if(!m.getResult().equals("continue")){
            //battle ends
            doUnbindService();
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();//finishing activity
        }
        else{
            //battle continues
            soldiers = m.getSoldiers();
            monsters = m.getMonsters();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    soldierHp.setText(Integer.toString(soldiers.get(0).getHp()));
                    soldierAtk.setText(Integer.toString(soldiers.get(0).getAtk()));
                    monsterHp.setText(Integer.toString(monsters.get(0).getHp()));
                    monsterAtk.setText(Integer.toString(monsters.get(0).getAtk()));
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void checkAttributeResult(final AttributeResultMessage m){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(Soldier s : m.getSoldiers()){
                    soldierHp.setText(Integer.toString(s.getHp()));
                    soldierAtk.setText(Integer.toString(s.getAtk()));
                    soldierID = s.getId();
                }
            }
        });
    }
}
