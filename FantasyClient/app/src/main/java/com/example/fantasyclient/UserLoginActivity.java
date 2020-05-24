package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.fantasyclient.json.JsonHandler;
import com.example.fantasyclient.json.LoginRecv;
import com.example.fantasyclient.json.LoginSend;

/**
 * This is login activity
 */
@SuppressLint("Registered")
public class UserLoginActivity extends UserBaseActivity{
    Button signUp;

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
                    sendData(new LoginSend("login", textUsername.getText().toString(),
                            textPassword.getText().toString()));

                    //receive data from server
                    String result = recvData();

                    //deserialize feedback from server
                    LoginRecv loginRecv = new LoginRecv();
                    JsonHandler jsonHandler = new JsonHandler(loginRecv);
                    loginRecv = (LoginRecv) jsonHandler.deserialize(loginRecv,result);

                    //check login result
                    if (loginRecv.getStatus().equals("success")) {
                        launchGame();
                    } else {
                        String errorMsg = loginRecv.getError_msg();
                        Log.e("Sign Up", errorMsg);
                        socketService.errorAlert(errorMsg);
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

    @Override
    protected void findView(){
        super.findView();
        signUp = findViewById(R.id.sign_up);
    }
}
