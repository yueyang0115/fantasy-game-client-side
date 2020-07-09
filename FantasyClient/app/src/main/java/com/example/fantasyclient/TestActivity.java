package com.example.fantasyclient;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.fantasyclient.fragment.MapFragment;

public class TestActivity extends BaseActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        fragmentManager = getSupportFragmentManager();
        findView();
        setOnClickListener();
    }

    @Override
    protected void findView(){
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, new MapFragment())
                .commit();
    }

    @Override
    protected void setOnClickListener(){
        final MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentByTag("mapFragment");
        mapFragment.getClickableGridView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapFragment.zoomUp();
            }
        });
    }
}
