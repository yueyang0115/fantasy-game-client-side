package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.fantasyclient.helper.ItemArrayAdapter;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.ShopRequestMessage;
import com.example.fantasyclient.json.ShopResultMessage;
import com.example.fantasyclient.model.ItemPack;

import java.util.List;
import java.util.Map;

public class ShopActivity extends BaseActivity {

    Button btn_buy, btn_sell, btn_cancel;
    int terrID, shopID;
    ItemPack currItemPack;
    List<ItemPack> shopItemList, inventoryItemList;
    Map<Integer,Integer> itemMap;//Map to store numbers of items to act on
    ItemArrayAdapter shopAdapter, inventoryAdapter;
    ListView shopListView, inventoryListView;
    ShopResultMessage shopResultMessage;
    InventoryResultMessage inventoryResultMessage;
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
        btn_buy = findViewById(R.id.btn_buy);
        btn_sell = findViewById(R.id.btn_sell);
        btn_cancel = findViewById(R.id.btn_cancel);
        shopListView = findViewById(R.id.shop_item_list);
        inventoryListView = findViewById(R.id.inventory_item_list);
    }

    @Override
    protected void initView(){
        shopAdapter = new ItemArrayAdapter(this, shopItemList);
        shopListView.setAdapter(shopAdapter);
        inventoryAdapter = new ItemArrayAdapter(this, inventoryItemList);
        inventoryListView.setAdapter(inventoryAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        shopResultMessage = (ShopResultMessage) intent.getSerializableExtra("ShopResultMessage");
        assert shopResultMessage != null;
        checkShopResult(shopResultMessage);
        inventoryResultMessage = (InventoryResultMessage) intent.getSerializableExtra("InventoryResultMessage");
        assert inventoryResultMessage != null;
        checkInventoryResult(inventoryResultMessage);
        terrID = intent.getIntExtra("territoryID",0);
        shopID = intent.getIntExtra("ShopID",0);
    }

    @Override
    protected void setOnClickListener(){
        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currItemPack = (ItemPack) parent.getItemAtPosition(position);
                Log.d(TAG,"Current item is "+ currItemPack.getItem().getName());
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new ShopRequestMessage(shopID,terrID, shopAdapter.getItemMap(),"buy")));
                handleRecvMessage(socketService.dequeue());
            }
        });

        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new ShopRequestMessage(shopID,terrID, inventoryAdapter.getItemMap(),"sell")));
                handleRecvMessage(socketService.dequeue());
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
        }
    }

    @Override
    protected void checkInventoryResult(final InventoryResultMessage m){
        if (m.getResult().equals("valid")) {
            Log.d(TAG,"checkInventoryResult");
            inventoryItemList = m.getItems();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    inventoryAdapter.clear();
                    inventoryAdapter.addAll(shopItemList);
                    inventoryAdapter.notifyDataSetChanged();
                }
            });
        }
    }


}
