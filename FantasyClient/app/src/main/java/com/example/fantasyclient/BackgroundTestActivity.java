package com.example.fantasyclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.fantasyclient.adapter.MapTerritoryAdapter;
import com.example.fantasyclient.model.WorldCoord;

public class BackgroundTestActivity extends BaseActivity {

    Button btnTest;
    MapTerritoryAdapter terrainAdapter = new MapTerritoryAdapter(this, new WorldCoord());
    MapTerritoryAdapter unitAdapter = new MapTerritoryAdapter(this, new WorldCoord());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background);

        findView();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terrainAdapter.updateCurrCoord(new WorldCoord(0,0));
                //terrainAdapter.addToCacheByCoords(new WorldCoord(0,-1),R.drawable.tree_e);
                terrainAdapter.notifyDataSetChanged();
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