package com.example.fantasyclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.fantasyclient.helper.ImageAdapter;

import java.util.HashMap;

public class BackgroundTestActivity extends BaseActivity {

    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    HashMap<Integer, ImageView> imageMap;
    /*imageView10,
            imageView11, imageView12, imageView13, imageView14, imageView15, imageView16, imageView17, imageView18, imageView19, imageView20,
            imageView21, imageView22, imageView23, imageView24, imageView25;*/
    Button btnTest;
    ImageAdapter adapter = new ImageAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background);

        findView();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.updateImage(10,R.drawable.ocean00);
                adapter.notifyDataSetChanged();
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
        GridView gridview = (GridView) findViewById(R.id.gridView);
        adapter.initImage();
        gridview.setAdapter(adapter);
    }
}