package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.adapter.SkillInfoAdapter;
import com.example.fantasyclient.fragment.InventoryListFragment;
import com.example.fantasyclient.fragment.MenuButtonFragment;
import com.example.fantasyclient.fragment.UnitListFragment;
import com.example.fantasyclient.json.LevelUpRequestMessage;
import com.example.fantasyclient.json.LevelUpResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.json.RedirectMessage;
import com.example.fantasyclient.model.Skill;
import com.example.fantasyclient.model.Unit;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends BaseActivity implements MenuButtonFragment.MenuButtonListener {

    //final constant
    static final String TAG = "MenuActivity";//tag for log
    Button btnBack;
    FragmentTransaction ft;
    UnitListFragment unitListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initFragment();
        findView();
        initView();
        getExtra();
        doBindService();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        currMessage = (MessagesS2C) intent.getSerializableExtra("CurrentMessage");
        assert currMessage != null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setListener(){
        btnBack.setOnClickListener(v -> {
            socketService.enqueue(new MessagesC2S(new RedirectMessage("MENU")));
        });
    }

    @Override
    protected void checkLevelUpResult(LevelUpResultMessage m){
        if(m.getResult().equals("start")){
            setUpSkillDialog(new ArrayList<>(m.getAvailableSkills()),m.getUnit());
        }
        else {
            unitListFragment.updateByElement(m.getUnit());
        }
    }

    @Override
    protected void checkRedirectResult(RedirectMessage m){
        if(m.getDestination().equals("MAIN")){
            finishActivity();
        }
        else{
            Log.e(TAG, "Error: invalid redirect message received");
        }
    }

    protected void finishActivity(){
        socketService.clearQueue();
        doUnbindService();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    protected void setUpSkillDialog(final List<Skill> list, final Unit unit){

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Please choose a skill to learn");
        // add a radio button list
        final SkillInfoAdapter adapter = new SkillInfoAdapter(MenuActivity.this, list, unit);
        int checkedItem = 0; // default is the first choice
        final Skill[] currSkill = new Skill[1];
        //set initial selected building to be the first
        if(!list.isEmpty()) {
            currSkill[0] = adapter.getItem(checkedItem);
        }
        builder.setSingleChoiceItems(adapter, checkedItem, (dialog, which) -> {
            // user checked an item
            currSkill[0] = adapter.getItem(which);
            //highlight selected item
            adapter.setHighlightedPosition(which);
            //updateAdapter(adapter, list);
            runOnUiThread(adapter::notifyDataSetChanged);
        });
        // add OK and Cancel buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            // user clicked OK
            if(currSkill[0]!=null) {
                socketService.enqueue(new MessagesC2S(new LevelUpRequestMessage("choose", unit.getId(), currSkill[0])));
                handleRecvMessage(socketService.dequeue());
            }
        });
        builder.setNegativeButton("Cancel", null);
        // create and show the alert dialog
        runOnUiThread(()->{
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    @Override
    public void doWithInventoryButton() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.elementListLayout, new InventoryListFragment(currMessage.getInventoryResultMessage().getItems()));
        removeDetailFragment(ft);
        ft.commit();
    }

    @Override
    public void doWithSoldierButton() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.elementListLayout, new UnitListFragment(new ArrayList<>(currMessage.getAttributeResultMessage().getSoldiers())));
        removeDetailFragment(ft);
        ft.commit();
    }

    protected void removeDetailFragment(FragmentTransaction ft){
        Fragment currDetailFragment = getSupportFragmentManager().findFragmentById(R.id.elementDetailLayout);
        if(currDetailFragment!=null) {
            ft.remove(currDetailFragment);
        }
    }
}