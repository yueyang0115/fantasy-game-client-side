package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.fantasyclient.json.*;

/**
 * This is sign up activity
 */
@SuppressLint("Registered")
public class UserSignUpActivity extends UserBaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findView();

        //bind socket service
        doBindService();

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //ensure all required data has been entered
                if(checkDataEntered()){
                    sendAndRecv(new MessagesC2S(new SignUpRequestMessage(textUsername.getText().toString(),
                            textPassword.getText().toString())));
                }
            }
        });

        redirect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLogin();
            }
        });
    }

    @Override
    protected void findView(){
        super.findView();
    }
}
