package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


@SuppressLint("Registered")
public class UserLoginActivity extends UserBaseActivity{
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textUsername = findViewById(R.id.username);
        textPassword = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        signUp = findViewById(R.id.sign_up);
        //startService(new Intent(UserLoginActivity.this, SocketService.class));
        doBindService();

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDataEntered()){
                    String msg = JsonHandler.serialUserInfo(textUsername.getText().toString(),
                            textPassword.getText().toString(),"login");
                    socketService.sendTcpMsg(msg);
                    String result = socketService.recvTcpMsg();
                    try {
                        JSONObject jsonResult = new JSONObject(result);
                        if(jsonResult.getString("status").equals("success")){
                            launchGame();
                        } else{
                            String errorMsg = jsonResult.getString("error_msg");
                            Log.e("Login",errorMsg);
                            socketService.errorAlert(errorMsg);
                        }
                    } catch (JSONException e) {
                        Log.e("Login","Invalid JSON user information");
                    }
                }
            }
        });

        signUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSignUp();
            }
        });
    }
}
