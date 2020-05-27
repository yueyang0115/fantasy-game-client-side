package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.fantasyclient.json.*;

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

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //ensure all required data has been entered
                if(checkDataEntered()){
                    //serialize sign up information and send to server
                    socketService.sendTcpMsg(new MessagesC2S(new LoginRequestMessage(textUsername.getText().toString(),
                            textPassword.getText().toString())));
                    handleRecvMessage(socketService.recvTcpMsg());
                }
            }
        });

        redirect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSignUp();
            }
        });
    }

    @Override
    protected void findView(){
        super.findView();
    }
}
