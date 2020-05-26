package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.fantasyclient.json.*;

/**
 * This is a template activity related to user accounts
 */
@SuppressLint("Registered")
public class UserBaseActivity extends BaseActivity{
    EditText textUsername;
    EditText textPassword;
    Button submit;
    Button redirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * check if all required data has been entered
     */
    protected boolean checkDataEntered(){
        if (isEmpty(textUsername)) {
            socketService.errorAlert("Please enter username!");
            return false;
        }
        if (isEmpty(textPassword)) {
            socketService.errorAlert("Please enter password!");
            return false;
        }
        return true;
    }

    /**
     * This is the unique method for account-related activities
     */
    public void sendAndRecv(MessagesC2S m){
        //serialize sign up information and send to server
        sendData(m);
        //receive data from server
        String result = recvData();
        //handle result
        handleRecvMessage(MessageHelper.deserialize(result));
    }

    /**
     * check if a blank has been entered
     * @param text to be entered
     * @return boolean
     */
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    /**
     * find required common views which may be overrode
     */
    @Override
    protected void findView(){
        textUsername = findViewById(R.id.username);
        textPassword = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        redirect = findViewById(R.id.redirect);
    }

}
