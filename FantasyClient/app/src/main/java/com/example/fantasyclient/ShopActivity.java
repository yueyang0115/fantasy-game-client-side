package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fantasyclient.adapter.InventoryArrayAdapter;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.ShopRequestMessage;
import com.example.fantasyclient.json.ShopResultMessage;
import com.example.fantasyclient.model.Inventory;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity of shopping, which maintains two item lists of both shop and inventory
 */
public class ShopActivity extends BaseActivity {

    //Buttons and TextViews
    Button btn_buy, btn_sell, btn_cancel;
    TextView text_money;

    //Lists to construct adapters
    List<Inventory> shopInventoryList = new ArrayList<>();
    List<Inventory> inventoryItemList = new ArrayList<>();

    //Adapters to show ListView
    InventoryArrayAdapter shopAdapter, inventoryAdapter;
    ListView shopListView, inventoryListView;

    //Cached messages passed by other activities
    ShopResultMessage shopResultMessage;
    InventoryResultMessage inventoryResultMessage;
    WorldCoord currCoord;

    final static String TAG = "ShopActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        findView();
        initView();
        doBindService();
        getExtra();
        setOnClickListener();
    }

    @Override
    protected void findView(){
        //Buttons
        btn_buy = findViewById(R.id.btn_buy);
        btn_sell = findViewById(R.id.btn_sell);
        btn_cancel = findViewById(R.id.btn_cancel);
        //TextViews
        text_money = findViewById(R.id.text_money);
        //ListViews
        inventoryListView = findViewById(R.id.inventory_item_list);
        shopListView = findViewById(R.id.shop_item_list);
    }

    @Override
    protected void initView(){
        inventoryAdapter = new InventoryArrayAdapter(this, inventoryItemList);
        inventoryListView.setAdapter(inventoryAdapter);
        shopAdapter = new InventoryArrayAdapter(this, shopInventoryList);
        shopListView.setAdapter(shopAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        shopResultMessage = (ShopResultMessage) intent.getSerializableExtra("ShopResultMessage");
        assert shopResultMessage != null;
        inventoryResultMessage = shopResultMessage.getInventoryResultMessage();
        checkShopResult(shopResultMessage);
        currCoord = (WorldCoord) intent.getSerializableExtra("ShopCoord");
    }

    @Override
    protected void setOnClickListener(){
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send shop request
                socketService.enqueue(new MessagesC2S(new ShopRequestMessage(currCoord,shopAdapter.getItemMap(),"buy")));
                handleRecvMessage(socketService.dequeue());
                shopAdapter.clearMap();
            }
        });
        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new ShopRequestMessage(currCoord,inventoryAdapter.getItemMap(),"sell")));
                handleRecvMessage(socketService.dequeue());
                inventoryAdapter.clearMap();
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
    }

    /**
     * This method is called after a MessageS2C with BattleResultMessage is received from server
     * UI and cached map will be updated based on the received message
     * @param m: received BattleResultMessage
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void checkShopResult(final ShopResultMessage m) {
        if (m.getResult().equals("valid")) {
            //action is valid, updateUI
            Log.d(TAG,"checkShopResult");
            shopInventoryList = m.getItems();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateAdapter(shopAdapter,shopInventoryList);
                }
            });
            checkInventoryResult(m.getInventoryResultMessage());
        }
        else{
            //action is invalid, show error message
            socketService.errorAlert(m.getResult());
        }
    }

    @SuppressLint("SetTextI18n")
    protected void checkInventoryResult(final InventoryResultMessage m){
        if (m.getResult().equals("valid")) {
            //action is valid, update UI
            inventoryItemList = m.getItems();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateAdapter(inventoryAdapter,inventoryItemList);
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
