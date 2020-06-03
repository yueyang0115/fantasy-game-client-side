package com.example.fantasyclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fantasyclient.helper.*;
import com.example.fantasyclient.json.*;
import com.example.fantasyclient.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends BaseActivity {

    static final String TAG = "MainActivity";
    static final int PERMISSIONS_REQUEST_LOCATION = 1;
    static final int BATTLE = 2;
    static final int SHOP = 3;
    SimpleLocation location;
    VirtualPosition vPosition = new VirtualPosition(0,0);
    Territory currTerr;
    ImageAdapter terrainAdapter = new ImageAdapter(this);
    ImageAdapter unitAdapter = new ImageAdapter(this);
    LocationTimerHandler locationTimerHandler;
    SendTimerHandler sendTimerHandler;
    boolean ifPause = false;
    List<Soldier> soldiers = new ArrayList<>();
    HashSet<Territory> cachedMap = new HashSet<>();
    TextView textLocation, textVLocation;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        // construct a new instance of SimpleLocation
        location = new SimpleLocation(this, true, false, 5 * 1000, true);
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
            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // make the device update its location
        updateLocation();
        new Thread() {
            @Override
            public void run() {
                locationTimerHandler = new LocationTimerHandler(vPosition,location);
                locationTimerHandler.handleTask(0,1000);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                while (socketService == null) {
                }
                sendTimerHandler = new SendTimerHandler(vPosition, socketService.sender);
                sendTimerHandler.handleTask(0,1000);
            }
        }.start();
        //Thread to receive feedback from server
        new Thread() {
            @Override
            public void run() {
                while (socketService == null) {
                }
                startRecvTerr();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        super.onPause();
        location.endUpdates();
        locationTimerHandler.cancelTask();
        sendTimerHandler.cancelTask();
        ifPause = true;
    }

    /**
     * this AsyncTask runs in background
     */
    public void startRecvTerr() {
        while(!ifPause){
            if(!socketService.receiver.isEmpty()){
                handleRecvMessage(socketService.receiver.dequeue());
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
        unitAdapter.initMap(R.drawable.transparent);
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
                unitAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void checkAttributeResult(final AttributeResultMessage m){
        soldiers = m.getSoldiers();
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
            if(position == 64){
                currTerr = t;
            }
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
            if(!t.getMonsters().isEmpty()) {
                switch (t.getMonsters().get(0).getType()) {
                    case "wolf":
                        unitAdapter.updateImage(position, R.drawable.wolf);
                        break;
                }
            }

        }
    }

    protected void launchBattle(){
        Intent intent = new Intent(this,BattleActivity.class);
        intent.putExtra("territoryID", currTerr.getId());
        startActivityForResult(intent,BATTLE);
    }

    /**
     * this method is called after "startActivityForResult"
     * it handles different return situation from another activity based on:
     * @param requestCode: determine if the activity is to view or modify
     * @param resultCode: determine if the player confirm or cancel
     *                  RESULT_OK: confirm; RESULT_CANCELED: cancel
     * @param data: Intent to get data submitted by players
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //resume updating location
        ifPause = false;
        //check the previous activity
        switch(requestCode){
            case BATTLE:
                //check the result of battle
                switch(resultCode){
                    case RESULT_WIN:
                        unitAdapter.updateImage(64, R.drawable.transparent);
                        break;
                    case RESULT_LOSE:
                    case RESULT_ESCAPED:
                        break;
                    default:
                        Log.e(TAG,"Invalid result code for battle");
                        break;
                }
                break;
            case SHOP:
                //check the result of purchase
                switch (resultCode){
                    default:
                        Log.e(TAG, "Invalid result code for shop");
                        break;
                }
            default:
                Log.e(TAG,"Invalid request code");
                break;
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
        unitGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==64 && !currTerr.getMonsters().isEmpty()){
                    launchBattle();
                }
            }
        });
    }
}