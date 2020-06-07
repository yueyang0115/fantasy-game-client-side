package com.example.fantasyclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This is an activity to show players' inventory
 */
public class InventoryActivity extends ItemActivity {

    Button btn_use, btn_drop;
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
}
