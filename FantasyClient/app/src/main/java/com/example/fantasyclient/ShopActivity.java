package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fantasyclient.adapter.InventoryPickerAdapter;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.json.ShopRequestMessage;
import com.example.fantasyclient.json.ShopResultMessage;
import com.example.fantasyclient.model.Inventory;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity of shopping, which maintains two item lists of both Shop and inventory
 */
public class ShopActivity extends BaseActivity {

    //Buttons and TextViews
    Button btn_buy, btn_sell, btn_cancel;
    TextView text_money;

    //Lists to construct adapters
    List<Inventory> shopInventoryList = new ArrayList<>();
    List<Inventory> inventoryItemList = new ArrayList<>();

    //Adapters to show ListView
    InventoryPickerAdapter shopAdapter, inventoryAdapter;
    ListView shopListView, inventoryListView;

    final static String TAG = "ShopActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        findView();
        initView();
        doBindService();
        getExtra();
        setListener();
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
        inventoryAdapter = new InventoryPickerAdapter(this, inventoryItemList);
        inventoryListView.setAdapter(inventoryAdapter);
        shopAdapter = new InventoryPickerAdapter(this, shopInventoryList);
        shopListView.setAdapter(shopAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        currCoord = (WorldCoord) intent.getSerializableExtra("ShopCoord");
        currMessage = (MessagesS2C) intent.getSerializableExtra("CurrentMessage");
        assert currMessage != null;
        checkShopResult(currMessage.getShopResultMessage());
        checkInventoryResult(currMessage.getInventoryResultMessage());
    }

    @Override
    protected void setListener(){
        btn_buy.setOnClickListener(v -> {
            //send Shop request
            socketService.enqueue(new MessagesC2S(new ShopRequestMessage(currCoord,shopAdapter.getSelectedItems(),"buy")));
            handleRecvMessage(socketService.dequeue());
            shopAdapter.clearMap();
        });
        btn_sell.setOnClickListener(v -> {
            socketService.enqueue(new MessagesC2S(new ShopRequestMessage(currCoord,inventoryAdapter.getSelectedItems(),"sell")));
            handleRecvMessage(socketService.dequeue());
            inventoryAdapter.clearMap();
        });
        btn_cancel.setOnClickListener(v -> {
            //clear queue before change activities
            socketService.clearQueue();
            doUnbindService();
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
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
            shopInventoryList = m.getItems();
            runOnUiThread(() -> updateAdapter(shopAdapter,shopInventoryList));
        }
        else{
            //action is invalid, show error message
            toastAlert(m.getResult());
        }
    }

    @SuppressLint("SetTextI18n")
    protected void checkInventoryResult(final InventoryResultMessage m){
        if (m.getResult().equals("valid")) {
            //action is valid, update UI
            inventoryItemList = m.getItems();
            runOnUiThread(() -> updateAdapter(inventoryAdapter,inventoryItemList));
            text_money.setText(Integer.toString(m.getMoney()));
        }
        else{
            //action is invalid, show error message
            toastAlert(m.getResult());
        }
    }
}
