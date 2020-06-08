package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BattleResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.model.Monster;
import com.example.fantasyclient.model.Soldier;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a template activity related to user accounts
 */
@SuppressLint("Registered")
public class BattleActivity extends BaseActivity{
    Button attackBtn, escapeBtn;
    ImageView soldierImg1, soldierImg2, soldierImg3, monsterImg1, monsterImg2, monsterImg3, seqImg1, seqImg2, seqImg3, seqImg4;
    TextView soldierAtk1, soldierHp1, soldierAtk2, soldierHp2, soldierAtk3, soldierHp3,
            monsterAtk1, monsterHp1, monsterAtk2, monsterHp2, monsterAtk3, monsterHp3;
    List<Soldier> soldiers = new ArrayList<>();
    List<Monster> monsters = new ArrayList<>();
    List<Integer> sequence = new ArrayList<>();
    int terrID, monsterID, soldierID = 1;
    boolean ifStop = false;
    BattleResultMessage battleResultMessage;
    static final String TAG = "BattleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        findView();
        doBindService();
        getExtra();
        setOnClickListener();

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
        soldierImg1 = findViewById(R.id.soldier1_view);
        soldierImg2 = findViewById(R.id.soldier2_view);
        soldierImg3 = findViewById(R.id.soldier3_view);
        monsterImg1 = findViewById(R.id.monster1_view);
        monsterImg2 = findViewById(R.id.monster2_view);
        monsterImg3 = findViewById(R.id.monster3_view);
        seqImg1 = findViewById(R.id.sequence1_view);
        seqImg2 = findViewById(R.id.sequence2_view);
        seqImg3 = findViewById(R.id.sequence3_view);
        seqImg4 = findViewById(R.id.sequence4_view);
        soldierHp1 = findViewById(R.id.soldier1_hp);
        soldierAtk1 = findViewById(R.id.soldier1_atk);
        monsterHp1 = findViewById(R.id.monster1_hp);
        monsterAtk1 = findViewById(R.id.monster1_atk);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        battleResultMessage = (BattleResultMessage) intent.getSerializableExtra("BattleResultMessage");
        assert battleResultMessage != null;
        checkBattleResult(battleResultMessage);
    }

    @Override
    protected void setOnClickListener(){
        monsterImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monsterID = monsters.get(0).getId();
                Log.d(TAG, "Choose monster1");
            }
        });

        soldierImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldierID = soldiers.get(0).getId();
                Log.d(TAG, "Choose soldier1");
            }
        });

        attackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage(terrID,monsterID,soldierID,"attack")));
            }
        });

        escapeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage("escape")));
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
            soldiers = m.getSoldiers();
            monsters = m.getMonsters();
            sequence = m.getUnitIDs();
            monsterID = monsters.get(0).getId();
            soldierID = soldiers.get(0).getId();
            for(Soldier s : soldiers){
                if(s.getId()==sequence.get(0)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            seqImg1.setImageResource(R.drawable.pichachu_battle);
                        }
                    });
                    break;
                }
            }
            for(Monster monster : monsters){
                if(monster.getId()==sequence.get(0)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            seqImg1.setImageResource(R.drawable.wolf_battle);
                        }
                    });
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    soldierHp1.setText("HP: "+Integer.toString(soldiers.get(0).getHp()));
                    soldierAtk1.setText("ATK: "+Integer.toString(soldiers.get(0).getAtk()));
                    soldierImg1.setImageResource(R.drawable.pichachu_battle);
                    monsterHp1.setText("HP: "+Integer.toString(monsters.get(0).getHp()));
                    monsterAtk1.setText("ATK: "+Integer.toString(monsters.get(0).getAtk()));
                    monsterImg1.setImageResource(R.drawable.wolf_battle);
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
