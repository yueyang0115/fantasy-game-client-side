package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    List<Soldier> soldiers = new ArrayList<>();
    List<Monster> monsters = new ArrayList<>();
    int terrID;
    static final String TAG = "BattleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        findView();
        doBindService();
        Intent intent = getIntent();
        terrID = intent.getIntExtra("territoryID",0);

        new Thread(){
            @Override
            public void run() {
                while (socketService==null){
                    Log.d(TAG, "Initial thread");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        Log.e(TAG,"Sleep");
                    }
                }
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage(terrID,0,0,"start")));
                handleRecvMessage(socketService.dequeue());
            }
        }.start();

        attackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage(terrID,monsters.get(0).getId(),soldiers.get(0).getId(),"attack")));
                handleRecvMessage(socketService.dequeue());
            }
        });

        escapeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage(terrID,monsters.get(0).getId(),soldiers.get(0).getId(),"escape")));
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
        if (m.getResult().equals("continue")) {
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
        } else {
            //battle ends
            doUnbindService();
            Intent intent = new Intent();
            switch (m.getResult()) {
                case "escaped":
                    setResult(RESULT_ESCAPED, intent);
                    break;
                case "win":
                    setResult(RESULT_WIN, intent);
                    break;
                case "lose":
                    setResult(RESULT_LOSE, intent);
                    break;
                default:
                    Log.e(TAG, "Invalid battle result received");
                    break;
            }
            finish();//finishing activity
        }
    }
}
