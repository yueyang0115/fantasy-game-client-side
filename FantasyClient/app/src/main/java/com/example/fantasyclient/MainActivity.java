package com.example.fantasyclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ServiceConnection;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends Activity {

    static final int PERMISSIONS_REQUEST_LOCATION = 1;
    SimpleLocation location;
    TextView textLocation, textVLocation;
    SocketService socketService;
    boolean mIsBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLocation = (TextView) findViewById(R.id.position);
        textVLocation = (TextView) findViewById(R.id.v_position);
        Button btnTest = (Button) findViewById(R.id.button1);
        // ...

        // construct a new instance of SimpleLocation
        location = new SimpleLocation(this, true, false, 5 * 1000, true);
        //location = new SimpleLocation(this);

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        startService(new Intent(MainActivity.this, SocketService.class));
        doBindService();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // TODO
                updateLocation();
                //location.beginUpdates();
                final double latitude = location.getLatitude();
                final double longitude = location.getLongitude();
                textLocation.setText("X:" + latitude + " Y:" + longitude);

                JSONObject jsonPoint = new JSONObject();
                try {
                    jsonPoint.put("x", latitude);
                    jsonPoint.put("y", longitude);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final JSONObject jsonLocation = new JSONObject();
                try{
                    jsonLocation.put("position", jsonPoint);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    socketService.send_msg(jsonLocation.toString()+"\n");
                    String v_position = socketService.recv_msg();

                    JSONObject jsonVLocation = new JSONObject(v_position);
                    JSONObject jsonVPoint = jsonVLocation.getJSONObject("v_position");
                    double v_latitude = jsonVPoint.getDouble("x");
                    double v_longitude = jsonVPoint.getDouble("y");
                    textVLocation.setText("X:" + v_latitude + " Y:" + v_longitude);
                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

        });

        /*location.setListener(new SimpleLocation.Listener() {

            public void onPositionChanged() {
                // new location data has been received and can be accessed
                JSONObject jsonPoint = new JSONObject();
                try {
                    jsonPoint.put("x", location.getLatitude());
                    jsonPoint.put("y", location.getLongitude());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jsonLocation = new JSONObject();
                try{
                    jsonLocation.put("position", jsonPoint);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new updateTask().execute(jsonLocation.toString());
            }

        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // make the device update its location
        //location.beginUpdates();
        updateLocation();

        // ...
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        location.endUpdates();

        // ...

        super.onPause();
    }

    /**
     * this AsyncTask runs in background when someone lose or win
     * it will receive msg from server and update UI
     * if game is over, a button to start a new game will appear
     */
    @SuppressLint("StaticFieldLeak")
    class updateTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String v_position = "";
            try {
                socketService.send_msg(params[0]+"/n");
                v_position = socketService.recv_msg();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return v_position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("v_position", result); // this is expecting a response code to be sent from your server upon receiving the POST data

            try {
                JSONObject jsonVLocation = new JSONObject(result);
                JSONObject jsonVPoint = jsonVLocation.getJSONObject("v_position");
                final double latitude = jsonVPoint.getDouble("x");
                final double longitude = jsonVPoint.getDouble("y");
                textVLocation.setText("X:" + latitude + " Y:" + longitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void updateLocation(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);

                // PERMISSIONS_REQUEST_LOCATION is an app-defined int constant.
                // The callback method gets the result of the request.
            }
        } else {
            // Permission has already been granted
            location.beginUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    location.beginUpdates();
                } else {
                    // permission denied, disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
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
            System.out.println("try to bind");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            socketService = null;
        }

    };

    private void doBindService() {
        if (bindService(new Intent(MainActivity.this, SocketService.class), mConnection, Context.BIND_AUTO_CREATE)) {
            System.out.println("bind success");
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