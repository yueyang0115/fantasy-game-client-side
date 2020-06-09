package com.example.fantasyclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.fantasyclient.adapter.ImageAdapter;

public class BackgroundTestActivity extends BaseActivity {

    Button btnTest;
    ImageAdapter terrainAdapter = new ImageAdapter(this);
    ImageAdapter unitAdapter = new ImageAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background);

        findView();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terrainAdapter.updateImage(10,R.drawable.river);
                terrainAdapter.notifyDataSetChanged();
                unitAdapter.updateImage(100,R.drawable.wolf);
                unitAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void findView(){
        btnTest = (Button) findViewById(R.id.btn_start);
        GridView terrainGridView = (GridView) findViewById(R.id.terrainGridView);
        GridView unitGridView = (GridView) findViewById(R.id.unitGridView);
        terrainAdapter.initImage(R.drawable.base00);
        unitAdapter.initImage(R.drawable.transparent);
        terrainGridView.setAdapter(terrainAdapter);
        unitGridView.setAdapter(unitAdapter);
    }
}