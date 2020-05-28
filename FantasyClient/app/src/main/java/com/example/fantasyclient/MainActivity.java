package com.example.fantasyclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fantasyclient.helper.*;
import com.example.fantasyclient.json.*;
import com.example.fantasyclient.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends BaseActivity {

    static final int PERMISSIONS_REQUEST_LOCATION = 1;
    SimpleLocation location;
    VirtualPosition vPosition = new VirtualPosition(0,0);
    ImageAdapter adapter = new ImageAdapter(this);
    HashMap<Territory,Integer> cachedMap = new HashMap<>();
    Handler handler;
    TextView textLocation, textVLocation;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        handler = new Handler();
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
               /* new Thread(){
                    @Override
                    public void run() {
                        while(true){
                            if(!sendQueue.isEmpty()){
                                try {
                                    socketService.sendTcpMsg(sendQueue.take());
                                } catch (InterruptedException e) {
                                    Log.e("SendQueue","Dequeue fails");
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }.start();*/
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
        Timer timer = new Timer();
        TimerTask doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        updateLocation();
                        PositionHelper.convertVPosition(vPosition,location.getLatitude(),location.getLongitude());
                        PositionRequestMessage p = new PositionRequestMessage(vPosition.getX(),vPosition.getY());
                        socketService.sendTcpMsg(new MessagesC2S(p));
                    }
                });
            }
        };
        timer.schedule(doAsyncTask, 0, 5000); //execute in every 5000 ms
    }

    public void startRecvTerr() {
        Timer timer = new Timer();
        TimerTask doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        handleRecvMessage(socketService.recvTcpMsg());
                    }
                });
            }
        };
        timer.schedule(doAsyncTask, 0, 5000); //execute in every 5000 ms
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

    /*@Override
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
    }*/

    @Override
    protected void checkPositionResult(final PositionResultMessage m){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(HashMap.Entry<Territory,Integer> e: cachedMap.entrySet()){
                    int position = 64+(e.getKey().getX()-vPosition.getX())-10*(e.getKey().getY()-vPosition.getY());
                    updateMap(e.getKey(),position);
                }
                List<Territory> terrArray = m.getTerritoryArray();
                for(Territory t : terrArray){
                    int position = 64+(t.getX()-vPosition.getX())-10*(t.getY()-vPosition.getY());
                    updateMap(t,position);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    protected void updateMap(Territory t, int position){
        switch(t.getTerrain().getType()){
            case "grass":
                adapter.updateImage(position,R.drawable.plains00);
                cachedMap.put(t,R.drawable.plains00);
                break;
            case "mountain":
                adapter.updateImage(position,R.drawable.mountain00);
                cachedMap.put(t,R.drawable.mountain00);
                break;
            case "river":
                adapter.updateImage(position,R.drawable.ocean00);
                cachedMap.put(t,R.drawable.ocean00);
                break;
        }
    }

    @Override
    protected void findView(){
        textLocation = (TextView) findViewById(R.id.position);
        textVLocation = (TextView) findViewById(R.id.v_position);
        btnTest = (Button) findViewById(R.id.btn_start);
        GridView gridview = (GridView) findViewById(R.id.gridView);
        adapter.initImage();
        gridview.setAdapter(adapter);
    }
}