package com.example.fantasyclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.fantasyclient.helper.ImageAdapter;

public class BackgroundTestActivity extends BaseActivity {

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