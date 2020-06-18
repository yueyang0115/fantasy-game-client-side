package com.example.fantasyclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fantasyclient.adapter.MapAdapter;
import com.example.fantasyclient.adapter.MapBuildingAdapter;
import com.example.fantasyclient.adapter.MapTerritoryAdapter;
import com.example.fantasyclient.adapter.MapUnitAdapter;
import com.example.fantasyclient.helper.PositionHelper;
import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BattleResultMessage;
import com.example.fantasyclient.json.BuildingRequestMessage;
import com.example.fantasyclient.json.BuildingResultMessage;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.PositionRequestMessage;
import com.example.fantasyclient.json.PositionResultMessage;
import com.example.fantasyclient.json.ShopRequestMessage;
import com.example.fantasyclient.json.ShopResultMessage;
import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.Monster;
import com.example.fantasyclient.model.Territory;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.List;

import im.delight.android.location.SimpleLocation;

/**
 * This is the main activity for players to play on, basically it has elements as following:
 * 1. A multi-layers map to show terrain, units and building information of territories
 * 2. Several buttons to access players' inventory, soldiers and other stuff
 * 3. A location variable to track players' current locations
 * 4. A SocketService to keep sending to and receiving from the server
 */
public class MainActivity extends BaseActivity {

    //final constant
    static final String TAG = "MainActivity";//tag for log
    static final int TERRAIN_INIT = R.drawable.base01;
    static final int UNIT_INIT = R.drawable.transparent;
    static final int PERMISSIONS_REQUEST_LOCATION = 1;//request code for location permission

    //map data
    SimpleLocation location;//used to track current location

    WorldCoord currCoord = new WorldCoord(0,0);

    //fields to show map
    MapTerritoryAdapter territoryAdapter;
    MapUnitAdapter unitAdapter;
    MapBuildingAdapter buildingAdapter;//Adapters for map
    List<MapAdapter> adapterList = new ArrayList<>();
    GridView terrainGridView, unitGridView, buildingGridView;//GridViews for map

    boolean ifPause = false;//flag to stop threads
    TextView textLocation, textVLocation;
    Button btnBag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
        initLocation();
        //inform players to give location permission
        updateLocation();
        doBindService();
        setOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Thread to update location and send to server
        new Thread() {
            @Override
            public void run() {
                while (socketService == null) {
                }
                //send location request when players start the game after login
                //location.beginUpdates() needs looper
                Looper.prepare();
                sendLocationRequest();
                //send location request when players change their location
                location.setListener(new SimpleLocation.Listener() {
                    @Override
                    public void onPositionChanged() {
                        sendLocationRequest();
                    }
                });
                Looper.loop();
            }
        }.start();
        //Thread to receive feedback from server
        new Thread() {
            @Override
            public void run() {
                while (socketService == null) {
                }
                socketService.clearQueue();
                while(!ifPause){
                    if(!socketService.receiver.isEmpty()){
                        handleRecvMessage(socketService.receiver.dequeue());
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stop location updates and background threads
        location.endUpdates();
        ifPause = true;
    }

    /**
     * This method initializes the location
     */
    protected void initLocation(){
        // construct a new instance of SimpleLocation
        location = new SimpleLocation(this, true, false, 5 * 1000, true);
        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
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
            // Permission is not granted, should we show an explanation?
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

    /**
     * This method update current location and send request message of required terrain data to server
     */
    protected void sendLocationRequest(){
        //update current location
        updateLocation();
        //convert location data to virtual coordinate
        WorldCoord tempCoord = new WorldCoord();
        PositionHelper.convertVPosition(tempCoord,location.getLatitude(),location.getLongitude());
        //check if current location has changed
        if(!tempCoord.equals(currCoord)) {
            //update current coordinate of all layers of map, and queryList as well
            currCoord = tempCoord;
            for (MapAdapter adapter : adapterList) {
                adapter.updateCurrCoord(currCoord);
            }
            enqueuePositionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
        //update new terrains
        for(Territory t : m.getTerritoryArray()) {
            updateTerrain(t);
        }
        //update new monsters
        for(Monster monster : m.getMonsterArray()){
            updateMonster(monster);
        }
        //update new buildings
        for(Building b : m.getBuildingArray()){
            updateBuilding(b);
        }
        //update UI
        updateMapLayers();
    }

    protected void updateMapLayers(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                territoryAdapter.notifyDataSetChanged();
                unitAdapter.notifyDataSetChanged();
                buildingAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * This method is called after a MessageS2C with ShopResultMessage is received from server
     * It happens in MainActivity only if players try to enter shops and send "list"
     * After InventoryResultMessage is also received, ShopActivity will be launched
     * @param m: received ShopResultMessage
     */
    @Override
    protected void checkShopResult(final ShopResultMessage m){
        if (m.getResult().equals("valid")) {
            Intent intent = new Intent(this, ShopActivity.class);
            intent.putExtra("ShopResultMessage", m);
            intent.putExtra("territoryCoord", currCoord);
            intent.putExtra("ShopID", buildingAdapter.getCachedTargetByCoord(currCoord).getId());
            startActivityForResult(intent, SHOP);
        }
    }

    /**
     * This method is called after a MessageS2C with BattleResultMessage is received from server
     * It happens in MainActivity only if players try to battle with monsters and send "start"
     * After permission from server, BattleActivity will be launched
     * @param m: received BattleResultMessage
     */
    @Override
    protected void checkBattleResult(final BattleResultMessage m){
        if(m.getResult().equals("continue")){
            Intent intent = new Intent(this,BattleActivity.class);
            intent.putExtra("BattleResultMessage", m);
            intent.putExtra("territoryCoord", currCoord);
            startActivityForResult(intent,BATTLE);
        }
    }

    @Override
    protected void checkBuildingResult(BuildingResultMessage m){
        if(m.getResult().equals("success")){
            if(m.getAction().equals("createList") || m.getAction().equals("upgradeList")) {
                List<Building> buildingList = m.getBuildingList();

            }
        }
    }

    protected void setUpDialog(String title, List list){
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        // add a radio button list
        String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
        int checkedItem = 1; // cow
        builder.setSingleChoiceItems((ListAdapter) list, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
            }
        });
        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
            }
        });
        builder.setNegativeButton("Cancel", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * This method changes the source file array in several adapters for corresponding map layers
     * mainly including layers that are unlikely to change: terrain, building
     * UI will be updated when adapter.notifyDataSetChanged() is called on UI thread
     * @param t: target territory
     */
    protected void updateTerrain(Territory t){
        WorldCoord targetCoord = t.getCoord();
        //update terrain layer
        territoryAdapter.addToCacheByCoords(targetCoord,t);
    }

    /**
     * This method changes unit adapters with the following steps:
     * 1. Check if the unit with this id has been cached before, if so, remove it
     * 2. Update its new coordinate and add it the cache
     */
    protected void updateMonster(Monster m){
        //remove from cache if cached
        if(unitAdapter.checkCacheByTarget(m)){
            unitAdapter.removeFromCacheByTarget(m);
        }
        //update coordinate and cache it
        unitAdapter.addToCacheByCoords(m.getCoord(),m);
    }

    /**
     * This method cache building data and update building layer
     * Since building is unlikely to change territory, receiving new buildings does not require
     * check existing building cache
     * @param b
     */
    protected void updateBuilding(Building b){
        buildingAdapter.addToCacheByCoords(b.getCoord(),b);
    }

    /**
     * This method is called after "startActivityForResult" is finished
     * It handles different results returned from another activity based on:
     * @param requestCode: determine what the activity is: BATTLE, SHOP
     * @param resultCode: determine what the result is:
     *                  BATTLE: RESULT_WIN, RESULT_LOSE, RESULT_ESCAPED
     *                  SHOP: RESULT_CANCELED
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
                if(resultCode == RESULT_WIN){
                    unitAdapter.removeFromCacheByCoords(currCoord);
                    updateMapLayers();
                }
                break;
            case SHOP:
                //check the result of purchase
                break;
            case INVENTORY:
                break;
            default:
                Log.e(TAG,"Invalid request code");
        }
        //inform server that the player has switch to the map
        enqueuePositionRequest();
    }

    /**
     * This method enqueue position request to be sent to server
     */
    protected void enqueuePositionRequest(){
        //get the coordinates which need to be queried from server
        List<WorldCoord> queriedCoords = territoryAdapter.getQueriedCoords();
        //enqueue query message in order to send to server
        PositionRequestMessage p = new PositionRequestMessage(queriedCoords, currCoord);
        socketService.enqueue(new MessagesC2S(p));
    }

    @Override
    protected void findView(){
        textLocation = (TextView) findViewById(R.id.position);
        textVLocation = (TextView) findViewById(R.id.v_position);
        btnBag = (Button) findViewById(R.id.btn_bag);
        terrainGridView = (GridView) findViewById(R.id.terrainGridView);
        unitGridView = (GridView) findViewById(R.id.unitGridView);
        buildingGridView = (GridView) findViewById(R.id.buildingGridView);

    }

    @Override
    protected void initView(){
        territoryAdapter = new MapTerritoryAdapter(this, currCoord);
        unitAdapter = new MapUnitAdapter(this, currCoord);
        buildingAdapter = new MapBuildingAdapter(this, currCoord);

        adapterList.add(territoryAdapter);
        adapterList.add(unitAdapter);
        adapterList.add(buildingAdapter);

        territoryAdapter.initImage(TERRAIN_INIT);
        unitAdapter.initImage(UNIT_INIT);
        buildingAdapter.initImage(UNIT_INIT);

        terrainGridView.setAdapter(territoryAdapter);
        unitGridView.setAdapter(unitAdapter);
        buildingGridView.setAdapter(buildingAdapter);
    }

    @Override
    protected void setOnClickListener(){
        unitGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //check if click on the center territory
                if(position==CENTER){
                    //check if current territory has monsters
                    if(unitAdapter.checkCacheByCoords(currCoord)){
                        socketService.enqueue(new MessagesC2S(
                                new BattleRequestMessage(currCoord, "start")));
                    }
                    //check if current territory has buildings
                    else if(buildingAdapter.checkCacheByCoords(currCoord)){
                        socketService.enqueue(new MessagesC2S(
                                new ShopRequestMessage(buildingAdapter.getCachedTargetByCoord(currCoord).getId(),"list")));
                    }
                    else{
                        socketService.enqueue(new MessagesC2S(
                                new BuildingRequestMessage(currCoord,"createList")
                        ));
                    }
                }
            }
        });

        btnBag.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // TODO
                socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("list")));
            }

        });
    }
}