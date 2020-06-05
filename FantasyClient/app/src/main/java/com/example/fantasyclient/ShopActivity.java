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
import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BattleResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.ShopRequestMessage;
import com.example.fantasyclient.json.ShopResultMessage;
import com.example.fantasyclient.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends BaseActivity {

    Button btn_buy, btn_sell, btn_cancel;
    int terrID, shopID;
    Item currItem;
    List<Item> itemList = new ArrayList<>();
    final static String TAG = "ShopActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        findView();
        //doBindService();
        Intent intent = getIntent();
        terrID = intent.getIntExtra("territoryID",0);
        shopID = intent.getIntExtra("ShopID",0);
        itemList.add(new Item("Example1",10));
        itemList.add(new Item("Example2",20));

        new Thread(){
            @Override
            public void run() {
                while (socketService==null){
                    Log.d(TAG, "Initial thread");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        Log.e(TAG,"Sleep");
                    }
                }
                socketService.enqueue(new MessagesC2S(new ShopRequestMessage(shopID,terrID,0,"list")));
                handleRecvMessage(socketService.dequeue());
            }
        }.start();

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new ShopRequestMessage(shopID,terrID,currItem.getId(),"buy")));
                handleRecvMessage(socketService.dequeue());
            }
        });

        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketService.enqueue(new MessagesC2S(new ShopRequestMessage(shopID,terrID,currItem.getId(),"sell")));
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
        if (m.getResult().equals("list")) {
            //shop starts
            itemList = m.getItems();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }

    @Override
    protected void findView(){
        btn_buy = findViewById(R.id.btn_buy);
        btn_sell = findViewById(R.id.btn_sell);
        btn_cancel = findViewById(R.id.btn_cancel);
        ListView listView = findViewById(R.id.item_list);
        ItemArrayAdapter adapter = new ItemArrayAdapter(this,R.layout.activity_shop,itemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currItem = (Item) parent.getItemAtPosition(position);
                Log.d(TAG,"Current item is "+ currItem.getName());
            }
        });
    }
}
