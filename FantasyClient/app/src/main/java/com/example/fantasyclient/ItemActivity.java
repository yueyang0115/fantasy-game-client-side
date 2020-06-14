package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fantasyclient.adapter.ItemArrayAdapter;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.model.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity which maintains a item list
 */
public class ItemActivity extends BaseActivity {

    Button btn_cancel;
    TextView text_money;
    List<Inventory> inventoryItemList = new ArrayList<>();
    ItemArrayAdapter inventoryAdapter;
    ListView inventoryListView;
    InventoryResultMessage inventoryResultMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void findView(){
        btn_cancel = findViewById(R.id.btn_cancel);
        text_money = findViewById(R.id.text_money);
        inventoryListView = findViewById(R.id.inventory_item_list);
    }

    @Override
    protected void initView(){
        inventoryAdapter = new ItemArrayAdapter(this, inventoryItemList);
        inventoryListView.setAdapter(inventoryAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        inventoryResultMessage = (InventoryResultMessage) intent.getSerializableExtra("InventoryResultMessage");
        assert inventoryResultMessage != null;
        checkInventoryResult(inventoryResultMessage);
    }

    @Override
    protected void setOnClickListener(){
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUnbindService();
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }

    /**
     * This method is called after a MessageS2C with InventoryResultMessage is received from server
     * UI and cached map will be updated based on the received message
     * @param m: received InventoryResultMessage
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void checkInventoryResult(final InventoryResultMessage m){
        if (m.getResult().equals("valid")) {
            //action is valid, update UI
            inventoryItemList = m.getItems();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    inventoryAdapter.clear();
                    inventoryAdapter.addAll(inventoryItemList);
                    inventoryAdapter.notifyDataSetChanged();
                }
            });
            text_money.setText(Integer.toString(m.getMoney()));
        }
        else{
            //action is invalid, show error message
            socketService.errorAlert(m.getResult());
        }
    }


}
