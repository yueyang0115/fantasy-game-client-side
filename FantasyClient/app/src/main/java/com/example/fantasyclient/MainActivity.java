package com.example.fantasyclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import java.util.HashSet;
import java.util.List;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends BaseActivity {

    static final int PERMISSIONS_REQUEST_LOCATION = 1;
    SimpleLocation location;
    VirtualPosition vPosition = new VirtualPosition(0,0);
    ImageAdapter terrainAdapter = new ImageAdapter(this);
    ImageAdapter unitAdapter = new ImageAdapter(this);
    HashSet<Territory> cachedMap = new HashSet<>();
    MessageSender sender = new MessageSender();
    MessageReceiver receiver = new MessageReceiver();
    TextView textLocation, textVLocation;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        // construct a new instance of SimpleLocation
        location = new SimpleLocation(this, true, false, 5 * 1000, true);
        //location = new SimpleLocation(this);

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }
        updateLocation();
        doBindService();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // TODO
                //Thread to update location
                new Thread() {
                    @Override
                    public void run() {
                        startUpdateLocation();
                    }
                }.start();
                //Thread to enqueue message to send queue
                new Thread() {
                    @Override
                    public void run() {
                        startSendLocation();
                    }
                }.start();
                //Thread to receive feedback from server
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        startRecvTerr();
                        Looper.loop();
                    }
                }.start();
                //Thread to keep sending message from queue
                new Thread(){
                    @Override
                    public void run() {
                        //ensure service is bound
                        while(socketService==null){}
                        sender.sendLoop(socketService.communicator);
                    }
                }.start();
                new Thread(){
                    @Override
                    public void run() {
                        //ensure service is bound
                        while(socketService==null){}
                        receiver.recvLoop(socketService.communicator);
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
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        location.endUpdates();
        super.onPause();
    }

    /**
     * this AsyncTask runs in background
     */
    protected void startUpdateLocation(){
        (new LocationTimerHandler(vPosition,location)).handleTask(0,1000);
    }

    public void startSendLocation() {
        (new SendTimerHandler(vPosition, sender)).handleTask(0,1000);
    }

    public void startRecvTerr() {
        while(true){
            if(!receiver.isEmpty()){
                handleRecvMessage(receiver.dequeue());
            }
        }
    }

    /**
     * These two methods ask for location permission
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

    /**
     * This method is called after a MessageS2C with PositionResultMessage is received from server
     * UI and cached map will be updated based on the received message
     * @param m: received PositionResultMessage
     */
    @Override
    protected void checkPositionResult(final PositionResultMessage m){
        //set background to be base type
        terrainAdapter.initMap(R.drawable.base00);
        //set cached territory
        for(Territory t : cachedMap){
            updateTerritory(t);
        }
        //update new territories
        List<Territory> terrArray = m.getTerritoryArray();
        for(Territory t : terrArray){
            updateTerritory(t);
            //add new territory to map cache
            cachedMap.add(t);
        }
        //update UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                terrainAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * This method change the source file array in ImageAdapter
     * UI will be updated when adapter.notifyDataSetChanged() is called
     * @param t: target territory
     */
    protected void updateTerritory(Territory t){
        int dx = (t.getX()-vPosition.getX())/10;
        int dy = (t.getY()-vPosition.getY())/10;
        if(dx>=-4 && dx<=5 && dy>=-7 && dy<=7) {
            int position = 64+dx-10*dy;
            switch (t.getTerrain().getType()) {
                case "grass":
                    terrainAdapter.updateImage(position, R.drawable.plains00);
                    break;
                case "mountain":
                    terrainAdapter.updateImage(position, R.drawable.mountain00);
                    break;
                case "river":
                    terrainAdapter.updateImage(position, R.drawable.ocean00);
                    break;
            }
            switch(t.getMonsters().get(0).getType()) {
                case "wolf":
                    unitAdapter.updateImage(position,R.drawable.wolf);
                    break;
            }
        }
    }

    @Override
    protected void findView(){
        textLocation = (TextView) findViewById(R.id.position);
        textVLocation = (TextView) findViewById(R.id.v_position);
        btnTest = (Button) findViewById(R.id.btn_start);
        GridView terrainGridView = (GridView) findViewById(R.id.terrainGridView);
        GridView unitGridView = (GridView) findViewById(R.id.unitGridView);
        terrainAdapter.initMap(R.drawable.base00);
        unitAdapter.initMap(R.drawable.transparent);
        terrainGridView.setAdapter(terrainAdapter);
        unitGridView.setAdapter(unitAdapter);
    }
}