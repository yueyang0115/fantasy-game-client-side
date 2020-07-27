package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.fragment.TalentTreeFragment;
import com.example.fantasyclient.json.LevelUpResultMessage;
import com.example.fantasyclient.json.MessagesS2C;

public class SoldierDetailActivity extends BaseActivity {

    //final constant
    static final String TAG = "SoldierDetailActivity";//tag for log
    Button btnBack;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soldier_detail);
        initFragment();
        findView();
        initView();
        doBindService();
        getExtra();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void checkLevelUpResult(LevelUpResultMessage m){

    }

    protected void initFragment(){
        ft = getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.soldierInfoLayout, new MenuButtonFragment());
        ft.replace(R.id.soldierSkillLayout, new TalentTreeFragment());
        ft.commit();
    }

    @Override
    protected void findView(){
        btnBack = (Button) findViewById(R.id.buttonBack);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        currMessage = (MessagesS2C) intent.getSerializableExtra("CurrentMessage");
        assert currMessage != null;
        checkLevelUpResult(currMessage.getLevelUpResultMessage());
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