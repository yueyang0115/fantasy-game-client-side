package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.SignUpRequestMessage;

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

        submit.setOnClickListener(v -> {
            //ensure all required data has been entered
            if(checkDataEntered()){
                socketService.enqueue(new MessagesC2S(new SignUpRequestMessage(textUsername.getText().toString(),
                        textPassword.getText().toString())));
                if(socketService.isEmpty()){
                }
                handleRecvMessage(socketService.dequeue());
            }
        });

        redirect.setOnClickListener(v -> launchLogin());
    }
}
