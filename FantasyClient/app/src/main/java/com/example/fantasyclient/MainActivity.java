package com.example.fantasyclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fantasyclient.json.JsonHandler;
import com.example.fantasyclient.json.PositionUpdateMessage;

import java.util.Timer;
import java.util.TimerTask;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends BaseActivity {

    static final int PERMISSIONS_REQUEST_LOCATION = 1;
    SimpleLocation location;
    TextView textLocation, textVLocation;
    //Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLocation = (TextView) findViewById(R.id.position);
        textVLocation = (TextView) findViewById(R.id.v_position);
        //btnTest = (Button) findViewById(R.id.btn_start);
        // ...

        // construct a new instance of SimpleLocation
        location = new SimpleLocation(this, true, false, 5 * 1000, true);
        //location = new SimpleLocation(this);

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        //startService(new Intent(MainActivity.this, SocketService.class));
        doBindService();

        new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        startSendLocation();
                    }
                });
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        startRecvTerr();
                    }
                });
            }
        }.start();

        /*btnTest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // TODO
                new Thread() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                startSendLocation();
                            }
                        });
                    }
                }.start();
                new Thread() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                startRecvTerr();
                            }
                        });
                    }
                }.start();
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
     * this AsyncTask runs in background
     */
    public void startSendLocation() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            updateLocation();
                            PositionUpdateMessage p = new PositionUpdateMessage(
                                    "position", location.getLatitude(),location.getLongitude());
                            String msg = (new JsonHandler(p)).serialize();
                            (new sendLocationTask()).execute(msg);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsyncTask, 0, 5000); //execute in every 5000 ms
    }

    @SuppressLint("StaticFieldLeak")
    class sendLocationTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... msg) {
            socketService.sendTcpMsg(msg[0]);
            return null;
        }
    }

    public void startRecvTerr() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            new recvTerrTask().execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsyncTask, 0, 5000); //execute in every 5000 ms
    }

    @SuppressLint("StaticFieldLeak")
    class recvTerrTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return socketService.recvTcpMsg();
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result!="") {
                try {
                    JSONObject jsonVPosition = new JSONObject(result);
                    JSONObject jsonVPoint = jsonVPosition.getJSONObject("v_position");
                    final double latitude = jsonVPoint.getDouble("x");
                    final double longitude = jsonVPoint.getDouble("y");
                    textVLocation.setText("X:" + latitude + " Y:" + longitude);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                Log.e("Receive", "empty");
            }
        }
    }

    /**
     * these two methods ask for location permission
     */
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
}