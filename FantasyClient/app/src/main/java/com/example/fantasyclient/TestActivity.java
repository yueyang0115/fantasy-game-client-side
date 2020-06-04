package com.example.fantasyclient;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        findView();
    }

    @Override
    protected void findView(){
    }
}
