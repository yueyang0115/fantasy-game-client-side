package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


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
}
