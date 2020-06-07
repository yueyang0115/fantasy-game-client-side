package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This is the start of game which create a service to keep connection with server
 */
public class StartActivity extends BaseActivity {

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnStart = (Button) findViewById(R.id.btn_start);

        startService(new Intent(StartActivity.this, SocketService.class));
        doBindService();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                launchLogin();
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
}