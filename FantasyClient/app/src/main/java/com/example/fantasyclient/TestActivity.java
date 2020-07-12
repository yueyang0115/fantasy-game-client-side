package com.example.fantasyclient;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.fragment.MapFragment;

public class TestActivity extends BaseActivity {

    MapFragment mapFragment = new MapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,mapFragment);
        ft.commit();
    }

    @Override
    protected void onResume() {

        super.onResume();
        setOnClickListener();
    }

    protected void setOnClickListener(){
        mapFragment.getClickableGridView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mapFragment.zoomUp();
            }
        });
    }
}
