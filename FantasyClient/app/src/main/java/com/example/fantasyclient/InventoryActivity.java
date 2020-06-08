package com.example.fantasyclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.fantasyclient.helper.ItemArrayAdapter;
import com.example.fantasyclient.helper.SoldierArrayAdapter;
import com.example.fantasyclient.json.AttributeResultMessage;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.model.Soldier;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity to show players' inventory
 */
public class InventoryActivity extends ItemActivity {

    Button btn_use, btn_drop;
    List<Soldier> soldierList = new ArrayList<>();
    SoldierArrayAdapter soldierAdapter;
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
    }

    @Override
    protected void initView(){
        super.initView();
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
            }
        });
        btn_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void checkAttributeResult(AttributeResultMessage m){
        soldierList = m.getSoldiers();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                soldierAdapter.clear();
                soldierAdapter.addAll();
            }
        });
        /*shopItemList = m.getItems();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                shopAdapter.clear();
                shopAdapter.addAll(shopItemList);
                shopAdapter.notifyDataSetChanged();
            }
        });*/
    }
}
