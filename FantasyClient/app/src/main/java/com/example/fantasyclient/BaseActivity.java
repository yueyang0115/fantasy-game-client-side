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

/**
 * This is base activity which contains several basic methods for all activities:
 * 1.Bind to socket service: communicate with server
 * 2.Redirect to other activities
 */
@SuppressLint("Registered")
public class BaseActivity extends Activity {

    SocketService socketService;
    boolean mIsBound;

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

    private void doUnbindService() {
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