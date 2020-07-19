package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.fragment.InventoryListFragment;
import com.example.fantasyclient.fragment.MenuButtonFragment;
import com.example.fantasyclient.fragment.SkillListFragment;
import com.example.fantasyclient.fragment.SoldierListFragment;
import com.example.fantasyclient.json.AttributeResultMessage;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.LevelUpResultMessage;
import com.example.fantasyclient.model.Unit;

import java.util.ArrayList;

public class MenuActivity extends BaseActivity {

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
    protected void checkAttributeResult(AttributeResultMessage m){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.elementListLayout, new SoldierListFragment(new ArrayList<Unit>(m.getSoldiers())));
        ft.commit();
    }

    @Override
    protected void checkInventoryResult(InventoryResultMessage m){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.elementListLayout, new InventoryListFragment(m.getItems()));
        ft.commit();
    }

    @Override
    protected void checkLevelUpResult(LevelUpResultMessage m){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.elementDetailLayout, new SkillListFragment(m.getAvailableSkills(),m.getUnit()));
        ft.replace(R.id.elementExtraLayout, new SkillListFragment(m.getUnit().getSkills(),m.getUnit()));
        ft.commit();
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
        btnBack.setOnClickListener(v -> {
            // TODO
            socketService.clearQueue();
            doUnbindService();
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        });
    }

}