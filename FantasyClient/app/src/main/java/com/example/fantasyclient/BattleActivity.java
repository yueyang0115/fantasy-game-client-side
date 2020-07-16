package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.fantasyclient.adapter.UnitImageAdapter;
import com.example.fantasyclient.adapter.UnitInfoAdapter;
import com.example.fantasyclient.json.AttributeRequestMessage;
import com.example.fantasyclient.json.AttributeResultMessage;
import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BattleResultMessage;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.model.BattleAction;
import com.example.fantasyclient.model.BattleInitInfo;
import com.example.fantasyclient.model.Unit;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a template activity related to user accounts
 */
@SuppressLint("Registered")
public class BattleActivity extends BaseActivity{
    Button attackBtn, itemBtn, escapeBtn;
    HashMap<Integer,Unit> unitMap = new HashMap<>();
    List<Unit> soldierList = new ArrayList<>();
    List<Unit> monsterList = new ArrayList<>();
    List<Unit> seqList = new ArrayList<>();
    List<Integer> defeatedMonsters = new ArrayList<>();//List to store defeated monsters
    Unit currSoldier, currMonster;//current attacker and attackee
    UnitInfoAdapter soldierAdapter, monsterAdapter;
    UnitImageAdapter seqAdapter;
    ListView soldierListView, monsterListView, seqListView;
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
        setListener();
    }

    @Override
    protected void findView(){
        attackBtn = findViewById(R.id.attack_btn);
        escapeBtn = findViewById(R.id.escape_btn);
        itemBtn = findViewById(R.id.item_btn);
        soldierListView = findViewById(R.id.soldier_list);
        monsterListView = findViewById(R.id.monster_list);
        seqListView = findViewById(R.id.sequence_list);
    }

    @Override
    protected void initView(){
        soldierAdapter = new UnitInfoAdapter(this, soldierList);
        soldierListView.setAdapter(soldierAdapter);
        monsterAdapter = new UnitInfoAdapter(this, monsterList);
        monsterListView.setAdapter(monsterAdapter);
        seqAdapter = new UnitImageAdapter(this, seqList);
        seqListView.setAdapter(seqAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        currCoord = (WorldCoord) intent.getSerializableExtra("territoryCoord");
        currMessage = (MessagesS2C) intent.getSerializableExtra("CurrentMessage");
        assert currMessage != null;
        checkBattleResult(currMessage.getBattleResultMessage());
    }

    @Override
    protected void setListener(){
        attackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monsterList.isEmpty() || soldierList.isEmpty()){
                    Log.e(TAG,"Battle has already ends");
                }
                else{
                    if(currMonster == null || currMonster.getHp() <= 0){
                        currMonster = monsterList.get(0);
                        selectMonster(0);
                    }
                    if(currSoldier == null || currSoldier.getHp() <= 0){
                        currSoldier = soldierList.get(0);
                        selectSoldier(0);
                    }
                    socketService.enqueue(new MessagesC2S(new BattleRequestMessage(currCoord,"attack",
                            new BattleAction(new Unit(currSoldier),new Unit(currMonster),"normal"))));
                    handleRecvMessage(socketService.dequeue());
                }

            }
        });

        itemBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // TODO
                socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("list")));
                handleRecvMessage(socketService.dequeue());
            }
        });

        escapeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage("escape")));
                handleRecvMessage(socketService.dequeue());
            }
        });

        soldierListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currSoldier = (Unit) parent.getItemAtPosition(position);
                selectSoldier(position);
            }
        });

        monsterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currMonster = (Unit) parent.getItemAtPosition(position);
                selectMonster(position);
            }
        });
    }

    /**
     * These two methods select specific unit in list and update UI to show it
     * @param position selected position
     */
    private void selectSoldier(int position){
        soldierAdapter.setHighlightedPosition(position);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateAdapter(soldierAdapter,soldierList);
            }
        });
    }

    private void selectMonster(int position){
        monsterAdapter.setHighlightedPosition(position);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateAdapter(monsterAdapter,monsterList);
            }
        });
    }

    private void updateEntireUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateAdapter(soldierAdapter,soldierList);
                updateAdapter(monsterAdapter,monsterList);
                updateAdapter(seqAdapter,seqList);
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
        if(!m.getResult().equals("escaped")) {
            BattleInitInfo initInfo = m.getBattleInitInfo();
            if (initInfo != null) {
                //At the start of battle, store initial units data into map
                soldierList = new ArrayList<Unit>(initInfo.getSoldiers());
                for (Unit u : soldierList) {
                    unitMap.put(u.getId(), u);
                }
                monsterList = new ArrayList<Unit>(initInfo.getMonsters());
                for (Unit u : monsterList) {
                    unitMap.put(u.getId(), u);
                }
                seqList.clear();
                for(Integer i : initInfo.getUnits()){
                    seqList.add(new Unit(unitMap.get(i)));
                }
            } else {
                //battle has already begun, handle received battle actions
                for (BattleAction action : m.getActions()) {
                    handleBattleAction(action);
                }
            }
            updateEntireUI();
            //after animations are done
            //if battle ends, finish activity
            if(!m.getResult().equals("continue")){
                finishActivity(m.getResult());
            }
        }
        else{
            //escaped, finish activity
            finishActivity(m.getResult());
        }
    }

    protected void finishActivity(String result){
        //clear queue before change activities
        socketService.clearQueue();
        doUnbindService();
        ifStop = true;
        Intent intent = new Intent();
        switch (result) {
            case "escaped":
                setResult(RESULT_ESCAPED, intent);
                break;
            case "win":
                setResult(RESULT_WIN, intent);
                intent.putIntegerArrayListExtra("defeatedMonsters", (ArrayList<Integer>) defeatedMonsters);
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

    /**
     * This method handles one battle action which contains attacker, attackee and action type
     * it updates cached information about related units
     * @param action BattleAction to be handled
     */
    protected void handleBattleAction(BattleAction action){
        Unit attacker = action.getAttacker();
        Unit attackee = action.getAttackee();
        //update unit map
        unitMap.put(attacker.getId(),attacker);
        unitMap.put(attackee.getId(),attackee);
        //update sequence list
        seqList.clear();
        for(Integer i : action.getUnits()){
            seqList.add(new Unit(unitMap.get(i)));
        }
        //update soldier and monster list
        updateUnitInfo(attacker);
        updateUnitInfo(attackee);
        //do animations
        animateActions();
    }

    /**
     * This method do animations of target battle action
     */
    protected void animateActions(){}

    /**
     * This method update unit list, which is shown by adapters, based on unit type
     * @param unit
     */
    protected void updateUnitInfo(Unit unit){
        if(unit.getType().equals("soldier")){
            updateUnitList(unit,soldierList);
        }
        else if(unit.getType().equals("monster")){
            updateUnitList(unit,monsterList);
        }
    }

    protected void updateUnitList(Unit unit, List<Unit> unitList){
        //find original index of this unit
        int index = unitList.indexOf(unit);
        //update its fields
        unitList.get(index).setFields(unit);
        //check if it dies in battle
        if(unit.getHp() <= 0) {
            unitList.remove(unit);
            //if monsters are defeated, store in list and return back to main activity
            if(unit.getType().equals("monster")) {
                defeatedMonsters.add(unit.getId());
            }
        }
    }

    /**
     * This method is called after returning from inventory activity
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check the previous activity
        switch(requestCode){
            case BATTLE:
            case SHOP:
                break;
            case INVENTORY:
                socketService.enqueue(new MessagesC2S(new AttributeRequestMessage("list")));
                handleRecvMessage(socketService.dequeue());
                break;
            default:
                Log.e(TAG,"Invalid request code");
        }
    }

    @Override
    protected void checkAttributeResult(AttributeResultMessage m){
        super.checkAttributeResult(m);
        soldierList = new ArrayList<Unit>(m.getSoldiers());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateAdapter(soldierAdapter,soldierList);
            }
        });
    }
}
