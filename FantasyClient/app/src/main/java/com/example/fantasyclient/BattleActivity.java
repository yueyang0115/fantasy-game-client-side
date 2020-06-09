package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.fantasyclient.adapter.*;
import com.example.fantasyclient.json.*;
import com.example.fantasyclient.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a template activity related to user accounts
 */
@SuppressLint("Registered")
public class BattleActivity extends BaseActivity{
    Button attackBtn, escapeBtn;
    List<Unit> soldierList = new ArrayList<>();
    List<Unit> monsterList = new ArrayList<>();
    List<Unit> seqList = new ArrayList<>();
    Soldier currSoldier;//current attacker
    Monster currMonster;//current attackee
    UnitArrayAdapter soldierAdapter, monsterAdapter;
    UnitImageAdapter seqAdapter;
    ListView soldierListView, monsterListView, seqListView;
    int terrID, monsterID, soldierID;
    boolean ifStop = false;
    BattleResultMessage battleResultMessage;
    static final String TAG = "BattleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        findView();
        initView();
        doBindService();
        getExtra();
        setOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Thread to receive feedback from server
        new Thread() {
            @Override
            public void run() {
                while (socketService == null) {
                }
                while(!ifStop){
                    if(!socketService.receiver.isEmpty()){
                        handleRecvMessage(socketService.receiver.dequeue());
                    }
                }
            }
        }.start();
    }

    @Override
    protected void findView(){
        attackBtn = findViewById(R.id.attack_btn);
        escapeBtn = findViewById(R.id.escape_btn);
        soldierListView = findViewById(R.id.soldier_list);
        monsterListView = findViewById(R.id.monster_list);
        seqListView = findViewById(R.id.sequence_list);
    }

    @Override
    protected void initView(){
        soldierAdapter = new UnitArrayAdapter(this, soldierList);
        soldierListView.setAdapter(soldierAdapter);
        monsterAdapter = new UnitArrayAdapter(this, monsterList);
        monsterListView.setAdapter(monsterAdapter);
        seqAdapter = new UnitImageAdapter(this, seqList);
        seqListView.setAdapter(seqAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        battleResultMessage = (BattleResultMessage) intent.getSerializableExtra("BattleResultMessage");
        assert battleResultMessage != null;
        checkBattleResult(battleResultMessage);
        terrID = intent.getIntExtra("territoryID",0);
    }

    @Override
    protected void setOnClickListener(){
        attackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monsterList.isEmpty() || soldierList.isEmpty()){
                    Log.e(TAG,"Battle has already ends");
                }
                else{
                    if(monsterID == 0){
                        monsterID = monsterList.get(0).getId();
                    }
                    if(soldierID == 0){
                        soldierID = soldierList.get(0).getId();
                    }
                    socketService.enqueue(new MessagesC2S(new BattleRequestMessage(terrID,"attack",
                            new BattleAction(soldierID,monsterID,"normal"))));
                }

            }
        });

        escapeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage("escape")));
            }
        });

        soldierListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currSoldier = (Soldier) parent.getItemAtPosition(position);
            }
        });

        monsterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currMonster = (Monster) parent.getItemAtPosition(position);
            }
        });
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
            //List<subType> cannot be cast to List<baseType> directly
            soldierList = new ArrayList<Unit>(m.getSoldiers());
            monsterList = new ArrayList<Unit>(m.getMonsters());
            seqList = m.getUnits();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    soldierAdapter.clear();
                    soldierAdapter.addAll(soldierList);
                    soldierAdapter.notifyDataSetChanged();
                    monsterAdapter.clear();
                    monsterAdapter.addAll(monsterList);
                    monsterAdapter.notifyDataSetChanged();
                    seqAdapter.clear();
                    seqAdapter.addAll(seqList);
                    seqAdapter.notifyDataSetChanged();
                }
            });
        } else {
            //battle ends
            doUnbindService();
            ifStop = true;
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
