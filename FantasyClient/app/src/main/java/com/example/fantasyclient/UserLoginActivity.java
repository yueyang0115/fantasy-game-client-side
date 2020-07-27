package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.fantasyclient.json.LoginRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;

/**
 * This is login activity
 */
@SuppressLint("Registered")
public class UserLoginActivity extends UserBaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();

        //bind socket service
        doBindService();

        submit.setOnClickListener(v -> {
            //ensure all required data has been entered
            if(checkDataEntered()){
                //serialize sign up information and send to server
                socketService.enqueue(new MessagesC2S(new LoginRequestMessage(textUsername.getText().toString(),
                        textPassword.getText().toString())));
                if(socketService.isEmpty()){
                }
                handleRecvMessage(socketService.dequeue());
            }
        });

        redirect.setOnClickListener(v -> launchSignUp());
    }
}
