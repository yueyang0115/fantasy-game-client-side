package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.fragment.SkillListFragment;
import com.example.fantasyclient.fragment.UnitListFragment;
import com.example.fantasyclient.json.AttributeRequestMessage;
import com.example.fantasyclient.json.AttributeResultMessage;
import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BattleResultMessage;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.model.BattleAction;
import com.example.fantasyclient.model.BattleInitInfo;
import com.example.fantasyclient.model.Skill;
import com.example.fantasyclient.model.Unit;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a template activity related to user accounts
 */
@SuppressLint("Registered")
public class BattleActivity extends BaseActivity implements UnitListFragment.UnitSelector, SkillListFragment.SkillSelector {
    Button attackBtn, itemBtn, escapeBtn;
    HashMap<Integer,Unit> unitMap = new HashMap<>();

    List<Integer> defeatedMonsters = new ArrayList<>();//List to store defeated monsters

    String actionType = "normal";
    UnitListFragment soldierListFragment, monsterListFragment, battleSequenceFragment;

    BattleResultMessage battleResultMessage;
    static final String TAG = "BattleActivity";
    FragmentTransaction ft;

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
        attackBtn.setOnClickListener(v -> {
            if(monsterListFragment.isEmpty() || soldierListFragment.isEmpty()){
                Log.e(TAG,"Battle has already ends");
            }
            else{
                socketService.enqueue(new MessagesC2S(new BattleRequestMessage(currCoord,"attack",
                        new BattleAction(new Unit(soldierListFragment.getSelectedElement()),new Unit(monsterListFragment.getSelectedElement()),actionType))));
                handleRecvMessage(socketService.dequeue());
            }

        });

        itemBtn.setOnClickListener(v -> {
            socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("list")));
            handleRecvMessage(socketService.dequeue());
        });

        escapeBtn.setOnClickListener(v -> {
            socketService.enqueue(new MessagesC2S(new BattleRequestMessage("escape")));
            handleRecvMessage(socketService.dequeue());
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
        battleResultMessage = m;
        String battleResult = m.getResult();
        switch (battleResult){
            case "continue":
                BattleInitInfo initInfo = m.getBattleInitInfo();
                if (initInfo != null) {
                    initListFragment(initInfo);
                    //updateListFragment();
                } else {
                    //battle has already begun, handle received battle actions
                    for (BattleAction action : m.getActions()) {
                        handleBattleAction(action);
                    }
                }
                //after animations are done
                break;
            case "lose":
                prepareLaunchDeathWorld();
                break;
            case "win":
            case "escaped":
                prepareLaunchMainWorld();
                break;
        }
    }

    private void initListFragment(BattleInitInfo initInfo){
        List<Unit> soldierList = new ArrayList<>(initInfo.getSoldiers());
        for (Unit u : soldierList) {
            unitMap.put(u.getId(), u);
        }
        List<Unit> monsterList = new ArrayList<>(initInfo.getMonsters());
        for (Unit u : monsterList) {
            unitMap.put(u.getId(), u);
        }
        List<Unit> sequenceList = new ArrayList<>();
        for(Integer i : initInfo.getUnits()){
            sequenceList.add(new Unit(unitMap.get(i)));
        }

        ft = getSupportFragmentManager().beginTransaction();
        if(soldierListFragment == null) {
            soldierListFragment = new UnitListFragment(soldierList, this);
            ft.replace(R.id.soldierListLayout, soldierListFragment);
        }
        if(monsterListFragment == null){
            monsterListFragment = new UnitListFragment(monsterList, this);
            ft.replace(R.id.monsterListLayout, monsterListFragment);
        }
        if(battleSequenceFragment == null){
            battleSequenceFragment = new UnitListFragment(sequenceList, this);
            ft.replace(R.id.battleSequenceLayout, battleSequenceFragment);
        }
        ft.commit();
    }

    /*private void updateListFragment(){
        soldierListFragment.updateByList(soldierList);
        monsterListFragment.updateByList(monsterList);
        battleSequenceFragment.updateByList(sequenceList);
    }*/

    @Override
    protected void finishActivity(){
        finishActivityByResult(battleResultMessage.getResult());
    }

    protected void finishActivityByResult(String result){
        //clear queue before change activities
        socketService.clearQueue();
        doUnbindService();
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
        List<Unit> sequenceList = new ArrayList<>();
        for(Integer i : action.getUnits()){
            sequenceList.add(new Unit(unitMap.get(i)));
        }
        battleSequenceFragment.updateByList(sequenceList);
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
            soldierListFragment.updateByElement(unit);
        }
        else if(unit.getType().equals("monster")){
            monsterListFragment.updateByElement(unit);
            if(unit.getHp()<=0) {
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
        soldierListFragment.updateByList(new ArrayList<>(m.getSoldiers()));
    }

    @Override
    public void doWithSelectedSkill(Skill skill) {
        actionType = skill.getName();
    }

    @Override
    public void doWithSelectedUnit(Unit unit) {
        if(unit.getType().equals("monster")){
            //do nothing here if just select a monster
        }
        else if(unit.getType().equals("soldier")){
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.skillListLayout, new SkillListFragment(new ArrayList<>(unit.getSkills()), unit, this));
            ft.commit();
        }
    }
}
