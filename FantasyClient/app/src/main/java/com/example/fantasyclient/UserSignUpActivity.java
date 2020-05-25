package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

                    //serialize sign up information and send to server
                    sendData(new SignUpSend("signup", textUsername.getText().toString(),
                                    textPassword.getText().toString()));

                    //receive data from server
                    String result = recvData();

                    //deserialize feedback from server
                    SignUpRecv signUpRecv = new SignUpRecv();
                    JsonHandler jsonHandler = new JsonHandler(signUpRecv);

                    signUpRecv = (SignUpRecv) jsonHandler.deserialize(signUpRecv,result);

                    //check sign up result
                    if (signUpRecv.getStatus().equals("success")) {
                        launchLogin();
                    } else {
                        String errorMsg = signUpRecv.getError_msg();
                        Log.e("Sign Up", errorMsg);
                        socketService.errorAlert(errorMsg);
                    }
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
