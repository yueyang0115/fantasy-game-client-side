package com.example.fantasyclient;

import android.os.Bundle;
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
 * This is an activity to show players' inventory as well as soldiers information
 */
public class InventoryActivity extends ItemActivity {

    Button btn_use, btn_drop;//button to use and drop item
    List<Unit> soldierList = new ArrayList<>();
    Soldier currSoldier;//current soldier to use item
    Inventory currInventory;//current item to use
    UnitArrayAdapter soldierAdapter;
    ListView soldierListView;
    AttributeResultMessage attributeResultMessage;
    final static String TAG = "InventoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        findView();
        initView();
        doBindService();
        getExtra();
        setOnClickListener();
    }

    @Override
    protected void findView(){
        super.findView();
        btn_use = findViewById(R.id.btn_use);
        btn_drop = findViewById(R.id.btn_drop);
        soldierListView = findViewById(R.id.soldier_list);
    }

    @Override
    protected void initView(){
        super.initView();
        soldierAdapter = new UnitArrayAdapter(this, soldierList);
        soldierListView.setAdapter(soldierAdapter);
    }

    @Override
    protected void getExtra(){
        super.getExtra();
        attributeResultMessage = inventoryResultMessage.getAttributeResultMessage();
        assert attributeResultMessage != null;
        checkAttributeResult(attributeResultMessage);
    }

    @Override
    protected void setOnClickListener(){
        super.setOnClickListener();
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inventoryItemList.isEmpty()){
                    socketService.errorAlert("Nothing to use");
                }
                else if(soldierList.isEmpty()){
                    socketService.errorAlert("No soldier to use on");
                }
                else {
                    if(currInventory == null) {
                        currInventory = inventoryItemList.get(0);
                    }
                    if(currSoldier == null){
                        currSoldier = (Soldier) soldierList.get(0);
                    }
                    socketService.clearQueue();
                    socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("use", currInventory.getId(), currSoldier.getId())));
                    handleRecvMessage(socketService.dequeue());
                }
            }
        });
        btn_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        soldierListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currSoldier = (Soldier) parent.getItemAtPosition(position);
            }
        });
        inventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currInventory = (Inventory) parent.getItemAtPosition(position);
            }
        });
    }

    @Override
    protected void checkInventoryResult(InventoryResultMessage m){
        super.checkInventoryResult(m);
        checkAttributeResult(m.getAttributeResultMessage());
    }

    @Override
    protected void checkAttributeResult(AttributeResultMessage m){
        soldierList = new ArrayList<Unit>(m.getSoldiers());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                soldierAdapter.clear();
                soldierAdapter.addAll(soldierList);
                soldierAdapter.notifyDataSetChanged();
            }
        });
    }
}
