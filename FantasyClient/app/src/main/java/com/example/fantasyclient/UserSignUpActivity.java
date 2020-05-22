package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import org.json.JSONException;
import org.json.JSONObject;


@SuppressLint("Registered")
public class UserSignUpActivity extends UserBaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        textUsername = findViewById(R.id.username);
        textPassword = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        doBindService();

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDataEntered()){
                    String msg = JsonHandler.serialUserInfo(textUsername.getText().toString(),
                            textPassword.getText().toString(),"signup");
                    socketService.sendTcpMsg(msg);
                    String result = socketService.recvTcpMsg();
                    Log.d("Result", result);
                    try {
                        JSONObject jsonResult = new JSONObject(result);
                        if(jsonResult.getString("status").equals("success")){
                            launchLogin();
                        } else{
                            String errorMsg = jsonResult.getString("error_msg");
                            Log.e("Sign Up",errorMsg);
                            socketService.errorAlert(errorMsg);
                        }
                    } catch (JSONException e) {
                        Log.e("Sign Up","Invalid JSON user information");
                    }
                }
            }
        });
    }
}
