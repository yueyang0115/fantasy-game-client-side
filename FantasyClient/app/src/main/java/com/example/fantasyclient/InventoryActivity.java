package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fantasyclient.adapter.InventoryInfoAdapter;
import com.example.fantasyclient.adapter.UnitInfoAdapter;
import com.example.fantasyclient.json.AttributeResultMessage;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.model.Inventory;
import com.example.fantasyclient.model.Soldier;
import com.example.fantasyclient.model.Unit;

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
    InventoryInfoAdapter inventoryAdapter;
    UnitInfoAdapter soldierAdapter;

    ListView soldierListView, inventoryListView;

    //Current selected target
    Inventory currInventory;//current item to use
    Soldier currSoldier;//current soldier to use item

    final static String TAG = "InventoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        findView();
        initView();
        doBindService();
        getExtra();
        setListener();
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
        inventoryAdapter = new InventoryInfoAdapter(this, inventoryItemList);
        inventoryListView.setAdapter(inventoryAdapter);
        soldierAdapter = new UnitInfoAdapter(this, soldierList);
        soldierListView.setAdapter(soldierAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        currMessage = (MessagesS2C) intent.getSerializableExtra("CurrentMessage");
        assert currMessage != null;
        checkInventoryResult(currMessage.getInventoryResultMessage());
        checkAttributeResult(currMessage.getAttributeResultMessage());
    }

    @Override
    protected void setListener(){
        //Buttons OnClickListener
        btn_use.setOnClickListener(v -> {
            if(inventoryItemList.isEmpty()){
                toastAlert("Nothing to use");
            }
            else if(soldierList.isEmpty()){
                toastAlert("No soldier to use on");
            }
            else {
                if(currInventory == null) {
                    currInventory = inventoryItemList.get(0);
                }
                if(currSoldier == null){
                    currSoldier = (Soldier) soldierList.get(0);
                }
                socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("use", currInventory.getId(), currSoldier.getId())));
                handleRecvMessage(socketService.dequeue());
            }
        });
        btn_drop.setOnClickListener(v -> {
            if(inventoryItemList.isEmpty()){
                toastAlert("Nothing to use");
            }
            else {
                if(currInventory == null) {
                    currInventory = inventoryItemList.get(0);
                }
                /*socketService.clearQueue();
                socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("drop", currInventory.getId(), currSoldier.getId())));
                handleRecvMessage(socketService.dequeue());*/
            }
        });
        btn_cancel.setOnClickListener(v -> {
            //clear queue before change activities
            socketService.clearQueue();
            doUnbindService();
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        });

        //ListViews OnClickListener
        soldierListView.setOnItemClickListener((parent, view, position, id) -> {
            currSoldier = (Soldier) parent.getItemAtPosition(position);
            selectSoldier(position);
        });
        inventoryListView.setOnItemClickListener((parent, view, position, id) -> {
            currInventory = (Inventory) parent.getItemAtPosition(position);
            selectInventory(position);
        });
    }

    /**
     * These two methods select specific unit in list and update UI to show it
     * @param position selected position
     */
    private void selectSoldier(int position){
        soldierAdapter.setHighlightedPosition(position);
        updateSoldierAdapter();
    }

    private void selectInventory(int position){
        inventoryAdapter.setHighlightedPosition(position);
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
            toastAlert(m.getResult());
        }
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
        runOnUiThread(() -> updateAdapter(soldierAdapter, soldierList));
    }

    private void updateInventoryAdapter(){
        runOnUiThread(() -> updateAdapter(inventoryAdapter,inventoryItemList));
    }
}
