package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.fragment.MapFragment;
import com.example.fantasyclient.model.WorldCoord;

public class TestActivity extends BaseActivity {

    MapFragment mapFragment = new MapFragment(currCoord);
    SeekBar zoomBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,mapFragment);
        ft.commit();
        zoomBar = (SeekBar) findViewById(R.id.zoomBar);
        textView = (TextView) findViewById(R.id.textView);
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

        GridView gridView = mapFragment.getClickableGridView();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                ClipData.Item item = new ClipData.Item("MAP");
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData("MAP",mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

                v.startDrag(dragData,myShadow,null,0);
                return false;
            }
        });

        gridView.setOnDragListener(new View.OnDragListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_LOCATION:
                        textView.setText("X:" + event.getX() + ", Y:" + event.getY());
                        mapFragment.updateCurrCoord(new WorldCoord(currCoord.getX()+1, currCoord.getY()+1));
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped, reassign View to ViewGroup
                        mapFragment.resetScreen();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
