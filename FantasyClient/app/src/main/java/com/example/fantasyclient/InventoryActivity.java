package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fantasyclient.adapter.*;
import com.example.fantasyclient.json.*;
import com.example.fantasyclient.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity to show players' inventory as well as soldiers information
 */
public class InventoryActivity extends BaseActivity {

    //Buttons and TextViews
    Button btn_use, btn_drop, btn_cancel;
    TextView text_money;

    //Lists to construct adapters
    List<Inventory> inventoryItemList = new ArrayList<>();
    List<Unit> soldierList = new ArrayList<>();

    //Adapters to show ListView
    InventoryArrayAdapter inventoryAdapter;
    UnitArrayAdapter soldierAdapter;

    ListView soldierListView, inventoryListView;

    //Current selected target
    Inventory currInventory;//current item to use
    Soldier currSoldier;//current soldier to use item

    //Cached messages passed by other activities
    AttributeResultMessage attributeResultMessage;
    InventoryResultMessage inventoryResultMessage;

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
        //Buttons
        btn_use = findViewById(R.id.btn_use);
        btn_drop = findViewById(R.id.btn_drop);
        btn_cancel = findViewById(R.id.btn_cancel);
        //textViews
        text_money = findViewById(R.id.text_money);
        //ListViews
        inventoryListView = findViewById(R.id.inventory_item_list);
        soldierListView = findViewById(R.id.soldier_list);
    }

    @Override
    protected void initView(){
        inventoryAdapter = new InventoryArrayAdapter(this, inventoryItemList);
        inventoryListView.setAdapter(inventoryAdapter);
        soldierAdapter = new UnitArrayAdapter(this, soldierList);
        soldierListView.setAdapter(soldierAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();

        inventoryResultMessage = (InventoryResultMessage) intent.getSerializableExtra("InventoryResultMessage");
        assert inventoryResultMessage != null;
        checkInventoryResult(inventoryResultMessage);

        attributeResultMessage = inventoryResultMessage.getAttributeResultMessage();
        assert attributeResultMessage != null;
        checkAttributeResult(attributeResultMessage);
    }

    @Override
    protected void setOnClickListener(){
        //Buttons OnClickListener
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
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUnbindService();
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });

        //ListViews OnClickListener
        soldierListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currSoldier = (Soldier) parent.getItemAtPosition(position);
                selectSoldier(position);
            }
        });
        inventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currInventory = (Inventory) parent.getItemAtPosition(position);
                selectInventory(position);
            }
        });
    }

    /**
     * These two methods select specific unit in list and update UI to show it
     * @param position selected position
     */
    private void selectSoldier(int position){
        soldierAdapter.setCurrPosition(position);
        updateSoldierAdapter();
    }

    private void selectInventory(int position){
        inventoryAdapter.setCurrPosition(position);
        updateInventoryAdapter();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void checkInventoryResult(InventoryResultMessage m){
        if (m.getResult().equals("valid")) {
            //action is valid, update UI
            inventoryItemList = m.getItems();
            updateInventoryAdapter();
            text_money.setText(Integer.toString(m.getMoney()));
        }
        else{
            //action is invalid, show error message
            socketService.errorAlert(m.getResult());
        }
        checkAttributeResult(m.getAttributeResultMessage());
    }

    @Override
    protected void checkAttributeResult(AttributeResultMessage m){
        soldierList = new ArrayList<Unit>(m.getSoldiers());
        updateSoldierAdapter();
    }

    /**
     * These methods update adapters on UI thread
     */
    private void updateSoldierAdapter(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                soldierAdapter.clear();
                soldierAdapter.addAll(soldierList);
                soldierAdapter.notifyDataSetChanged();
            }
        });
    }

    private void updateInventoryAdapter(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                inventoryAdapter.clear();
                inventoryAdapter.addAll(inventoryItemList);
                inventoryAdapter.notifyDataSetChanged();
            }
        });
    }
}
