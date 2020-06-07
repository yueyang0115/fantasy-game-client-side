package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.fantasyclient.helper.ItemArrayAdapter;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.ShopRequestMessage;
import com.example.fantasyclient.json.ShopResultMessage;
import com.example.fantasyclient.model.ItemPack;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity of shopping, which maintains two item lists of both shop and inventory
 */
public class ShopActivity extends ItemActivity {

    Button btn_buy, btn_sell;
    int terrID, shopID;
    List<ItemPack> shopItemList = new ArrayList<>();
    ItemArrayAdapter shopAdapter;
    ListView shopListView;
    ShopResultMessage shopResultMessage;
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
        super.findView();
        btn_buy = findViewById(R.id.btn_buy);
        btn_sell = findViewById(R.id.btn_sell);
        shopListView = findViewById(R.id.shop_item_list);
    }

    @Override
    protected void initView(){
        super.initView();
        shopAdapter = new ItemArrayAdapter(this, shopItemList);
        shopListView.setAdapter(shopAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        shopResultMessage = (ShopResultMessage) intent.getSerializableExtra("ShopResultMessage");
        assert shopResultMessage != null;
        inventoryResultMessage = shopResultMessage.getInventoryResultMessage();
        checkShopResult(shopResultMessage);
        terrID = intent.getIntExtra("territoryID",0);
        shopID = intent.getIntExtra("ShopID",0);
    }

    @Override
    protected void setOnClickListener(){
        super.setOnClickListener();
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send shop request
                socketService.enqueue(new MessagesC2S(new ShopRequestMessage(shopID,terrID, shopAdapter.getItemMap(),"buy")));
                handleRecvMessage(socketService.dequeue());
                shopAdapter.clearMap();
            }
        });
        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new ShopRequestMessage(shopID,terrID, inventoryAdapter.getItemMap(),"sell")));
                handleRecvMessage(socketService.dequeue());
                inventoryAdapter.clearMap();
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
            //shop starts
            Log.d(TAG,"checkShopResult");
            shopItemList = m.getItems();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    shopAdapter.clear();
                    shopAdapter.addAll(shopItemList);
                    shopAdapter.notifyDataSetChanged();
                }
            });
            checkInventoryResult(m.getInventoryResultMessage());
        }
    }
}
