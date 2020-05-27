package com.example.fantasyclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fantasyclient.json.MessageHelper;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.json.PositionRequestMessage;
import com.example.fantasyclient.json.PositionResultMessage;
import com.example.fantasyclient.json.Territory;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends BaseActivity {

    static final int PERMISSIONS_REQUEST_LOCATION = 1;
    SimpleLocation location;
    TextView textLocation, textVLocation;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    HashMap<Integer, ImageView> imageMap;
    /*imageView10,
            imageView11, imageView12, imageView13, imageView14, imageView15, imageView16, imageView17, imageView18, imageView19, imageView20,
            imageView21, imageView22, imageView23, imageView24, imageView25;*/
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        textLocation = (TextView) findViewById(R.id.position);
        textVLocation = (TextView) findViewById(R.id.v_position);
        btnTest = (Button) findViewById(R.id.btn_start);
        // ...

        // construct a new instance of SimpleLocation
        location = new SimpleLocation(this, true, false, 5 * 1000, true);
        //location = new SimpleLocation(this);

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        doBindService();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // TODO
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        startSendLocation();
                        Looper.loop();
                    }
                }.start();
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        startRecvTerr();
                        Looper.loop();
                    }
                }.start();
            }

        });
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
                            PositionRequestMessage p = new PositionRequestMessage(location.getLatitude(),location.getLongitude());
                            socketService.sendTcpMsg(new MessagesC2S(p));
                            //(new sendLocationTask()).execute(new MessagesC2S(p));
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
    class sendLocationTask extends AsyncTask<MessagesC2S, Void, Void> {
        @Override
        protected Void doInBackground(MessagesC2S... msg) {
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
                            //new recvTerrTask().execute();
                            handleRecvMessage(socketService.recvTcpMsg());
                        } catch (Exception e) {
                            Log.e("MainActivity","Failed to handle position result");
                            e.printStackTrace();
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsyncTask, 0, 5000); //execute in every 5000 ms
    }

    @SuppressLint("StaticFieldLeak")
    class recvTerrTask extends AsyncTask<Void, Void, MessagesS2C> {
        @Override
        protected MessagesS2C doInBackground(Void... voids) {
            return socketService.recvTcpMsg();
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(MessagesS2C result) {
            super.onPostExecute(result);
            handleRecvMessage(result);
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

    @Override
    protected void checkPositionResult(final PositionResultMessage m){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Territory> terrArray = m.getTerritoryArray();
                ImageView targetView = null;
                for(Territory t : terrArray){
                    targetView = imageMap.get(5+t.getX()-3*t.getY());
                    assert targetView != null;
                    switch(t.getTerrain().getType()){
                        case "grass":
                            targetView.setImageResource(R.drawable.plains00);
                            break;
                        case "mountain":
                            targetView.setImageResource(R.drawable.mountain00);
                            break;
                        case "river":
                            targetView.setImageResource(R.drawable.ocean00);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void findView(){
        textLocation = (TextView) findViewById(R.id.position);
        textVLocation = (TextView) findViewById(R.id.v_position);
        btnTest = (Button) findViewById(R.id.btn_start);
        imageMap = new HashMap<>();
        imageView1 = (ImageView) findViewById(R.id.imageView7);
        imageMap.put(1,imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView8);
        imageMap.put(2,imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView9);
        imageMap.put(3,imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView12);
        imageMap.put(4,imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView13);
        imageMap.put(5,imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView14);
        imageMap.put(6,imageView6);
        imageView7 = (ImageView) findViewById(R.id.imageView17);
        imageMap.put(7,imageView7);
        imageView8 = (ImageView) findViewById(R.id.imageView18);
        imageMap.put(8,imageView8);
        imageView9 = (ImageView) findViewById(R.id.imageView19);
        imageMap.put(9,imageView9);
    }
}