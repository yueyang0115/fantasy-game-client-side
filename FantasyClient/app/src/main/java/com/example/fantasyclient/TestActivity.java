package com.example.fantasyclient;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.fantasyclient.drawable.TextDrawable;

public class TestActivity extends BaseActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_image_layout);
        findView();
    }

    @Override
    protected void findView(){
        imageView = findViewById(R.id.unitImg);
        //imageView.setImageResource(R.drawable.pickachu_back);
        imageView.setImageDrawable(new TextDrawable(getResources(),"A"));
    }
}
