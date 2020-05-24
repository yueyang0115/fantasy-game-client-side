package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.fantasyclient.json.JsonBase;
import com.example.fantasyclient.json.JsonHandler;


@SuppressLint("Registered")
public class UserBaseActivity extends BaseActivity{
    EditText textUsername;
    EditText textPassword;
    Button submit;

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

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    protected void findView(){
        textUsername = findViewById(R.id.username);
        textPassword = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
    }

    protected void sendData(JsonBase target){
        JsonHandler jsonHandler = new JsonHandler(target);
        socketService.sendTcpMsg(jsonHandler.serialize());
    }

    protected String recvData(){
        String result = socketService.recvTcpMsg();
        //ensure feedback is received
        while(result.equals("")){
            Log.d("SignUp","Empty result, receive again");
            result = socketService.recvTcpMsg();
        }
        return result;
    }

}
