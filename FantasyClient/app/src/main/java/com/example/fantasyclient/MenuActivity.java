package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.adapter.SkillInfoAdapter;
import com.example.fantasyclient.fragment.InventoryDetailFragment;
import com.example.fantasyclient.fragment.InventoryListFragment;
import com.example.fantasyclient.fragment.MenuButtonFragment;
import com.example.fantasyclient.fragment.PlayerInfoDetailFragment;
import com.example.fantasyclient.fragment.PlayerInfoListFragment;
import com.example.fantasyclient.fragment.UnitDetailFragment;
import com.example.fantasyclient.fragment.UnitListFragment;
import com.example.fantasyclient.json.FriendRequestMessage;
import com.example.fantasyclient.json.FriendResultMessage;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.LevelUpRequestMessage;
import com.example.fantasyclient.json.LevelUpResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.model.Inventory;
import com.example.fantasyclient.model.PlayerInfo;
import com.example.fantasyclient.model.Skill;
import com.example.fantasyclient.model.Unit;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends BaseActivity implements PlayerInfoListFragment.PlayerSelector, InventoryListFragment.InventorySelector, UnitListFragment.UnitSelector, MenuButtonFragment.MenuButtonListener {

    //final constant
    static final String TAG = "MenuActivity";//tag for log
    Button btnBack;
    FragmentTransaction ft;
    UnitListFragment unitListFragment;
    InventoryListFragment inventoryListFragment;
    PlayerInfoListFragment playerInfoListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findView();
        initView();
        //get current message from previous activity or fragment
        getExtra();
        //use current message to initialize fragments
        initFragment();
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
            prepareLaunchMainWorld();
        });
    }

    @Override
    protected void checkLevelUpResult(LevelUpResultMessage m){
        Unit unit = m.getUnit();
        if(m.getResult().equals("start")){
            setUpSkillDialog(new ArrayList<>(m.getAvailableSkills()),unit);
        }
        else {
            unitListFragment.updateByElement(unit);
            unitListFragment.loadSoldierDetail(unit);
        }
    }

    @Override
    protected void checkInventoryResult(InventoryResultMessage m){
        inventoryListFragment.updateByList(m.getItems());
        doWithSelectedInventory(m.getItems().get(0));
    }

    @Override
    protected void checkFriendResult(FriendResultMessage m){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        playerInfoListFragment = new PlayerInfoListFragment(m.getPlayerInfoList(), this);
        ft.replace(R.id.elementListLayout, playerInfoListFragment);
        removeDetailFragment(ft);
        ft.commit();
    }

    @Override
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
        inventoryListFragment = new InventoryListFragment(currMessage.getInventoryResultMessage().getItems(), this);
        ft.replace(R.id.elementListLayout, inventoryListFragment);
        removeDetailFragment(ft);
        ft.commit();
    }

    @Override
    public void doWithSoldierButton() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        unitListFragment = new UnitListFragment(new ArrayList<>(currMessage.getAttributeResultMessage().getSoldiers()), this);
        ft.replace(R.id.elementListLayout, unitListFragment);
        removeDetailFragment(ft);
        ft.commit();
    }

    @Override
    public void doWithFriendButton() {
    }

    @Override
    public void doWithAddFriendButton() {
        setUpAddFriendButton();
    }

    private void setUpAddFriendButton(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setMessage("Enter Username");
        alert.setTitle("Find a friend by username");
        alert.setView(edittext);
        alert.setPositiveButton("Search", (dialog, whichButton) -> {
            String username = edittext.getText().toString();
            socketService.enqueue(new MessagesC2S(new FriendRequestMessage(username, FriendRequestMessage.ActionType.search)));
            handleRecvMessage(socketService.dequeue());
            dialog.dismiss();
        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            dialog.dismiss();
        });
        alert.show();
    }

    @Override
    public void doWithSelectedInventory(Inventory inventory) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        removeDetailFragment(ft);
        ft.replace(R.id.elementDetailLayout, new InventoryDetailFragment(inventory, new ArrayList<>(currMessage.getAttributeResultMessage().getSoldiers())));
        ft.commit();
    }

    @Override
    public void doWithSelectedUnit(Unit unit) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        removeDetailFragment(ft);
        ft.replace(R.id.elementDetailLayout, new UnitDetailFragment(unit));
        ft.commit();
    }

    protected void removeDetailFragment(FragmentTransaction ft){
        Fragment currDetailFragment = getSupportFragmentManager().findFragmentById(R.id.elementDetailLayout);
        if(currDetailFragment!=null) {
            ft.remove(currDetailFragment);
        }
    }

    @Override
    public void doWithSelectedPlayer(PlayerInfo playerInfo) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        removeDetailFragment(ft);
        ft.replace(R.id.elementDetailLayout, new PlayerInfoDetailFragment(playerInfo));
        ft.commit();
    }
}