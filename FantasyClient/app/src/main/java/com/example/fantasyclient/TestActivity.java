package com.example.fantasyclient;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.fragment.MapFragment;

public class TestActivity extends BaseActivity {

    MapFragment mapFragment = new MapFragment(currCoord);
    SeekBar zoomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,mapFragment);
        ft.commit();
        zoomBar = findViewById(R.id.zoomBar);
    }

    @Override
    protected void onResume() {

        super.onResume();
        setOnClickListener();
    }

    protected void setOnClickListener(){
        zoomBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int zoomLevel = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                zoomLevel = progress;
                mapFragment.zoom(zoomLevel);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
