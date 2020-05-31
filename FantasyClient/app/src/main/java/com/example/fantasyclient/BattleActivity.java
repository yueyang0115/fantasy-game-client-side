package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BattleResultMessage;
import com.example.fantasyclient.json.MessagesC2S;

/**
 * This is a template activity related to user accounts
 */
@SuppressLint("Registered")
public class BattleActivity extends BaseActivity{
    Button attackBtn;
    Button escapeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        findView();
        doBindService();
        Intent intent = getIntent();
        final int terrID = intent.getIntExtra("TerritoryID",0);
        final int monsterID = intent.getIntExtra("MonsterID",0);
        final int soldierID = 0;

        attackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.sendTcpMsg(new MessagesC2S(new BattleRequestMessage(terrID,monsterID,soldierID,"attack")));
                handleRecvMessage(socketService.recvTcpMsg());
            }
        });

        escapeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.sendTcpMsg(new MessagesC2S(new BattleRequestMessage(terrID,monsterID,soldierID,"escape")));
                handleRecvMessage(socketService.recvTcpMsg());
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
    }

    /**
     * This method is called after a MessageS2C with BattleResultMessage is received from server
     * UI and cached map will be updated based on the received message
     * @param m: received BattleResultMessage
     */
    @Override
    protected void checkBattleResult(final BattleResultMessage m){
        if(!m.getResult().equals("continue")){
            //battle ends
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();//finishing activity
        }
        else{
            //battle continues
        }
    }

}
