package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.fragment.InventoryListFragment;
import com.example.fantasyclient.fragment.MenuButtonFragment;
import com.example.fantasyclient.fragment.SkillListFragment;
import com.example.fantasyclient.fragment.SoldierListFragment;
import com.example.fantasyclient.json.AttributeRequestMessage;
import com.example.fantasyclient.json.AttributeResultMessage;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.LevelUpRequestMessage;
import com.example.fantasyclient.json.LevelUpResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.RedirectMessage;
import com.example.fantasyclient.model.Inventory;
import com.example.fantasyclient.model.Skill;
import com.example.fantasyclient.model.Unit;

import java.util.ArrayList;

public class MenuActivity extends BaseActivity implements
        MenuButtonFragment.OnMenuListener,
        SoldierListFragment.OnSoldierSelectedListener,
        InventoryListFragment.OnInventorySelectedListener,
        SkillListFragment.OnSkillSelectedListener {

    //final constant
    static final String TAG = "MenuActivity";//tag for log
    Button btnBack;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initFragment();
        findView();
        initView();
        doBindService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListener();
    }

    @Override
    public void onMenuSoldier() {
        socketService.enqueue(new MessagesC2S(new AttributeRequestMessage("list")));
        handleRecvMessage(socketService.dequeue());
    }

    @Override
    public void onMenuInventory() {
        socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("list")));
        handleRecvMessage(socketService.dequeue());
    }

    @Override
    public void onInventorySelected(Inventory inventory) {

    }

    @Override
    public void onSoldierSelected(Unit unit) {
        socketService.enqueue(new MessagesC2S(new LevelUpRequestMessage("start", unit.getId())));
        handleRecvMessage(socketService.dequeue());
    }

    @Override
    public void onSkillSelected(Skill skill) {

    }

    @Override
    protected void checkAttributeResult(AttributeResultMessage m){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detailLayout, new SoldierListFragment(new ArrayList<Unit>(m.getSoldiers())));
        ft.commit();
    }

    @Override
    protected void checkInventoryResult(InventoryResultMessage m){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detailLayout, new InventoryListFragment(m.getItems()));
        ft.commit();
    }

    @Override
    protected void checkLevelUpResult(LevelUpResultMessage m){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.detailLayout, new SkillListFragment(m.getAvailableSkills()));
        ft.commit();
    }

    protected void checkRedirectResult(RedirectMessage m){
    }

    protected void initFragment(){
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.buttonLayout, new MenuButtonFragment());
        ft.commit();
    }

    @Override
    protected void findView(){
        btnBack = (Button) findViewById(R.id.buttonBack);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setListener(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // TODO
                socketService.clearQueue();
                doUnbindService();
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }

}