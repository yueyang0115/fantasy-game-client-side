package com.example.fantasyclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fantasyclient.helper.ImageAdapter;
import com.example.fantasyclient.helper.SendTimerHandler;
import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BattleResultMessage;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.InventoryResultMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.PositionResultMessage;
import com.example.fantasyclient.json.ShopRequestMessage;
import com.example.fantasyclient.json.ShopResultMessage;
import com.example.fantasyclient.model.Soldier;
import com.example.fantasyclient.model.Territory;
import com.example.fantasyclient.model.VirtualPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    static final String TAG = "MainActivity";//tag for log
    static final int PERMISSIONS_REQUEST_LOCATION = 1;//request code for location permission
    static final int BATTLE = 2;//request code for battle
    static final int SHOP = 3;//request code for shop
    static final int INVENTORY = 4;//request code for inventory
    static final int CENTER = 64;//center of the map
    SimpleLocation location;//used to track current location
    VirtualPosition vPosition = new VirtualPosition(0,0);
    Territory currTerr;
    ImageAdapter terrainAdapter, unitAdapter, buildingAdapter;//Adapters for map
    GridView terrainGridView, unitGridView, buildingGridView;//GridViews for map
    SendTimerHandler sendTimerHandler;//handler to send location periodically
    //cached shop-related message to pass to ShopActivity;
    ShopResultMessage shopResultMessage;
    InventoryResultMessage inventoryResultMessage;
    boolean ifPause = false;//flag to stop threads
    List<Soldier> soldiers = new ArrayList<>();
    HashSet<Territory> cachedMap = new HashSet<>();//cached map which has been found
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
                sendTimerHandler = new SendTimerHandler(vPosition, location, socketService.sender);
                sendTimerHandler.handleTask(0,1000);
            }
        }.start();
        //Thread to receive feedback from server
        new Thread() {
            @Override
            public void run() {
                while (socketService == null) {
                }
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
        sendTimerHandler.cancelTask();
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
        //set background to be base type
        terrainAdapter.initMap(R.drawable.base00);
        unitAdapter.initMap(R.drawable.transparent);
        buildingAdapter.initMap(R.drawable.transparent);
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
            shopResultMessage = m;
            if(inventoryResultMessage!=null){
                launchShop();
            }
        }
    }

    /**
     * This method is called after a MessageS2C with InventoryResultMessage is received from server
     * After ShopResultMessage is also received, ShopActivity will be launched
     * @param m: received ShopResultMessage
     */
    @Override
    protected void checkInventoryResult(InventoryResultMessage m){
        if (m.getResult().equals("valid")) {
            inventoryResultMessage = m;
            if(shopResultMessage!=null) {
                launchShop();
            }
            else{
                launchInventory();
            }
        }
    }

    /**
     * Launch shop activity with necessary shop and inventory information
     */
    protected void launchShop(){
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra("ShopResultMessage", shopResultMessage);
        intent.putExtra("InventoryResultMessage", inventoryResultMessage);
        intent.putExtra("territoryID", currTerr.getId());
        intent.putExtra("ShopID", currTerr.getBuilding().getId());
        startActivityForResult(intent, SHOP);
    }

    /**
     * Launch inventory activity with necessary inventory information
     */
    protected void launchInventory(){
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra("InventoryResultMessage", inventoryResultMessage);
        startActivityForResult(intent, INVENTORY);
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
            intent.putExtra("territoryID", currTerr.getId());
            startActivityForResult(intent,BATTLE);
        }
    }

    /**
     * This method change the source file array in several adapters for corresponding map layers
     * UI will be updated when adapter.notifyDataSetChanged() is called on UI thread
     * @param t: target territory
     */
    protected void updateTerritory(Territory t){
        int dx = (t.getX()-vPosition.getX())/10;
        int dy = (t.getY()-vPosition.getY())/10;
        if(dx>=-4 && dx<=5 && dy>=-7 && dy<=7) {
            int position = CENTER+dx-10*dy;
            if(position == CENTER){
                currTerr = t;
            }
            //update terrain layer
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
            //update monster layer
            if(!t.getMonsters().isEmpty()) {
                switch (t.getMonsters().get(0).getType()) {
                    case "wolf":
                        unitAdapter.updateImage(position, R.drawable.wolf);
                        break;
                    default:
                }
            }
            //update building layer
            if(t.getBuilding()!=null){
                switch(t.getBuilding().getName()){
                    case "shop":
                        buildingAdapter.updateImage(position,R.drawable.dirt_village00);
                        break;
                    default:
                }
            }

        }
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
                switch(resultCode){
                    case RESULT_WIN:
                        unitAdapter.updateImage(CENTER, R.drawable.transparent);
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
                    case RESULT_CANCELED:
                        break;
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
        btnBag = (Button) findViewById(R.id.btn_bag);
        terrainGridView = (GridView) findViewById(R.id.terrainGridView);
        unitGridView = (GridView) findViewById(R.id.unitGridView);
        buildingGridView = (GridView) findViewById(R.id.buildingGridView);

    }

    @Override
    protected void initView(){
        terrainAdapter = new ImageAdapter(this);
        unitAdapter = new ImageAdapter(this);
        buildingAdapter = new ImageAdapter(this);
        terrainAdapter.initMap(R.drawable.base00);
        unitAdapter.initMap(R.drawable.transparent);
        buildingAdapter.initMap(R.drawable.transparent);
        terrainGridView.setAdapter(terrainAdapter);
        unitGridView.setAdapter(unitAdapter);
        buildingGridView.setAdapter(buildingAdapter);
    }

    @Override
    protected void setOnClickListener(){
        buildingGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==CENTER){
                    if(!currTerr.getMonsters().isEmpty())
                        socketService.enqueue(new MessagesC2S(
                                new BattleRequestMessage(currTerr.getId(), 0, 0, "start")));
                    else if(currTerr.getBuilding()!=null){
                        socketService.enqueue(new MessagesC2S(
                                new ShopRequestMessage(currTerr.getBuilding().getId(),currTerr.getId(),new HashMap<Integer, Integer>(),"list")));
                        socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("list")));
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