package com.example.fantasyclient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fantasyclient.json.*;

/**
 * This is base activity which contains several basic methods for all activities:
 * 1.Bind to socket service: communicate with server
 * 2.Redirect to other activities
 */
@SuppressLint("Registered")
public class BaseActivity extends Activity {

    SocketService socketService;
    boolean mIsBound;
    final static String TAG = "BaseActivity";
    static final int RESULT_ESCAPED = RESULT_CANCELED;
    static final int RESULT_WIN = RESULT_OK;
    static final int RESULT_LOSE = RESULT_FIRST_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * find required common views which may be overrode
     */
    protected void findView(){
    }

    protected void launchSignUp() {
        Intent intent = new Intent(this, UserSignUpActivity.class);
        startActivity(intent);
    }

    protected void launchGame() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void launchLogin(){
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }

    protected void handleRecvMessage(MessagesS2C m){
        if(m == null){
            Log.e(TAG, "HandleRecvMessage: Invalid result received");
        }
        else {
            if (m.getLoginResultMessage() != null) {
                checkLoginResult(m.getLoginResultMessage());
            }
            if (m.getSignUpResultMessage() != null) {
                checkSignUpResult(m.getSignUpResultMessage());
            }
            if (m.getPositionResultMessage() != null) {
                checkPositionResult(m.getPositionResultMessage());
            }
            if (m.getBattleResultMessage() != null) {
                checkBattleResult(m.getBattleResultMessage());
            }
            if (m.getAttributeResultMessage() != null) {
                checkAttributeResult(m.getAttributeResultMessage());
            }
            if (m.getShopResultMessage() != null) {
                checkShopResult(m.getShopResultMessage());
            }

        }
    }

    protected void checkLoginResult(LoginResultMessage m){
        if (m.getStatus().equals("success")) {
            launchGame();
        } else {
            String errorMsg = m.getError_msg();
            Log.e("Login", errorMsg);
            socketService.errorAlert(errorMsg);
        }
    }

    protected void checkSignUpResult(SignUpResultMessage m){
        if (m.getStatus().equals("success")) {
            launchLogin();
        } else {
            String errorMsg = m.getError_msg();
            Log.e("Sign Up", errorMsg);
            socketService.errorAlert(errorMsg);
        }
    }

    protected void checkPositionResult(PositionResultMessage m){}

    protected void checkBattleResult(BattleResultMessage m){}

    protected void checkAttributeResult(AttributeResultMessage m){}

    protected void checkShopResult(ShopResultMessage m){}

    /**
     * these methods are for service
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        //EDITED PART
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            socketService = ((SocketService.LocalBinder) service).getService();
            Log.d("Service","Try to bind");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            socketService = null;
        }

    };

    void doBindService() {
        if (bindService(new Intent(BaseActivity.this, SocketService.class), mConnection, Context.BIND_AUTO_CREATE)) {
            Log.d("Service","Bind succeed");
        }
        mIsBound = true;
        if (socketService != null) {
            socketService.IsBoundable();
        }
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
}