package com.example.fantasyclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.adapter.BuildingInfoAdapter;
import com.example.fantasyclient.adapter.TerritoryInfoAdapter;
import com.example.fantasyclient.fragment.MapFragment;
import com.example.fantasyclient.helper.PositionHelper;
import com.example.fantasyclient.json.BattleRequestMessage;
import com.example.fantasyclient.json.BuildingRequestMessage;
import com.example.fantasyclient.json.BuildingResultMessage;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.PositionRequestMessage;
import com.example.fantasyclient.json.PositionResultMessage;
import com.example.fantasyclient.json.ShopRequestMessage;
import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.Monster;
import com.example.fantasyclient.model.Territory;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.Collections;
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
    static final int PERMISSIONS_REQUEST_LOCATION = 1;//request code for location permission

    //map data
    SimpleLocation location;//used to track current location

    //fields to show map
    MapFragment map;
    private SeekBar zoomBar;

    boolean ifPause = false;//flag to stop threads
    TextView textLocation, textVLocation;
    //Button btnBag;
    ImageView bagImg, settingsImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        findView();
        initView();
        initLocation();
        //inform players to give location permission
        updateLocation();
        doBindService();
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

        setOnClickListener();
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
            map.updateCurrCoord(tempCoord);
            enqueuePositionRequest(false);
            currCoord.setByCoord(tempCoord);
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
        if(m.getTerritoryArray() != null) {
            for (Territory t : m.getTerritoryArray()) {
                map.updateTerrain(t);
            }
        }
        //update new monsters
        if(m.getMonsterArray() != null) {
            for (Monster monster : m.getMonsterArray()) {
                map.updateMonster(monster);
            }
        }
        //update new buildings
        if(m.getBuildingArray() != null) {
            for (Building b : m.getBuildingArray()) {
                map.updateBuilding(b);
            }
        }
        //update UI
        updateMapLayers();
    }

    protected void updateMapLayers(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                map.updateMapLayers();
            }
        });
    }

    @Override
    protected void checkBuildingResult(final BuildingResultMessage m){
        if(m.getResult().equals("success")){
            String action = m.getAction();
            switch (action) {
                case "createList":
                    setUpBuildingDialog(m.getBuildingList(), "create");
                    break;
                case "upgradeList":
                    setUpBuildingDialog(m.getBuildingList(), "upgrade");
                    break;
                case "create":
                case "upgrade":
                    map.updateBuilding(m.getBuilding());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            map.updateMapLayers();
                        }
                    });
                    break;
            }
        }
        else{
            toastAlert(m.getResult());
        }
    }

    protected void setUpBuildingDialog(final List<Building> list, final String title){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Please choose a building to " + title);
                // add a radio button list
                final BuildingInfoAdapter adapter = new BuildingInfoAdapter(MainActivity.this, list);
                int checkedItem = 0; // default is the first choice
                final Building[] currBuilding = new Building[1];
                //set initial selected building to be the first
                if(!list.isEmpty()) {
                    currBuilding[0] = adapter.getItem(checkedItem);
                }
                builder.setSingleChoiceItems(adapter, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user checked an item
                        currBuilding[0] = adapter.getItem(which);
                        //highlight selected item
                        adapter.setHighlightedPosition(which);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                //updateAdapter(adapter, list);
                            }
                        });
                    }
                });
                // add OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked OK
                        if(currBuilding[0]!=null) {
                            socketService.enqueue(new MessagesC2S(new BuildingRequestMessage(currCoord, title, currBuilding[0].getName())));
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    protected void setUpTerritoryDialog(final List<Territory> list){
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Territory Details");
        // add a radio button list
        final TerritoryInfoAdapter adapter = new TerritoryInfoAdapter(MainActivity.this, list);
        builder.setAdapter(adapter, null);
        builder.setNegativeButton("Cancel", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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
                    map.removeUnitByCoordAfterBattle(currCoord);
                    updateMapLayers();
                }
                //tame may be changed after battle, ask for data again
                map.updateTerritoryQueryAfterBattle();
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
        enqueuePositionRequest(false);
    }

    /**
     * This method enqueue position request to be sent to server
     */
    protected void enqueuePositionRequest(boolean ifCheckEmptyQuery){
        //get the coordinates which need to be queried from server
        List<WorldCoord> queriedCoords = map.getQueriedCoords();
        //if need to check query and query is empty, do not send message
        if(ifCheckEmptyQuery && queriedCoords.isEmpty()) {
            return;
        }
        //enqueue query message in order to send to server
        PositionRequestMessage p = new PositionRequestMessage(queriedCoords, currCoord);
        socketService.enqueue(new MessagesC2S(p));
    }

    protected void initFragment(){
        map = new MapFragment(currCoord);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,map);
        ft.commit();
    }

    @Override
    protected void findView(){
        textLocation = (TextView) findViewById(R.id.position);
        textVLocation = (TextView) findViewById(R.id.v_position);
        //btnBag = (Button) findViewById(R.id.btn_bag);
        bagImg = (ImageView) findViewById(R.id.bagImg);
        settingsImg = (ImageView) findViewById(R.id.settingsImg);
        zoomBar = (SeekBar) findViewById(R.id.zoomBar);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setOnClickListener(){
        //Get clickable gridView from mapFragment;
        GridView gridView = map.getClickableGridView();
        //short click on GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //check if click on the center territory
                if(position==CENTER) {
                    //check if current territory has monsters
                    if(map.checkUnitCacheByCoord(currCoord)){
                        socketService.enqueue(new MessagesC2S(
                                new BattleRequestMessage(currCoord, "start")));
                    }
                    //check if current territory has buildings
                    else if(map.checkBuildingCacheByCoord(currCoord)){
                        switch (map.getBuildingByPosition(position).getName()) {
                            case "shop":
                            case "super_shop":
                                socketService.enqueue(new MessagesC2S(
                                        new ShopRequestMessage(currCoord, "list")));
                                break;
                        }
                    }
                    else{
                        setUpTerritoryDialog(new ArrayList<>(Collections.singletonList(map.getTerritoryByPosition(position))));
                    }
                }
                else {
                    setUpTerritoryDialog(new ArrayList<>(Collections.singletonList(map.getTerritoryByPosition(position))));
                }
            }
        });
        //long click on GridView
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //check if click on the center territory
                if(position==CENTER){
                    //check if current territory has monsters
                    if(map.checkUnitCacheByCoord(currCoord)){
                        socketService.enqueue(new MessagesC2S(
                                new BattleRequestMessage(currCoord, "start")));
                    }
                    //check if current territory has buildings
                    else if(map.checkBuildingCacheByCoord(currCoord)){
                        socketService.enqueue(new MessagesC2S(
                                new BuildingRequestMessage(currCoord,"upgradeList")
                        ));
                    }
                    else{
                        socketService.enqueue(new MessagesC2S(
                                new BuildingRequestMessage(currCoord,"createList")
                        ));
                    }
                }
                return true;
            }
        });
        gridView.setOnTouchListener(new View.OnTouchListener() {
            //Max allowed duration for a "click", in milliseconds.
            private static final int MAX_CLICK_DURATION = 1000;
            //Max allowed distance to move during a "click", in DP.
            private static final int MAX_CLICK_DISTANCE = 15;
            private long pressStartTime;
            private float pressedX;
            private float pressedY;
            private boolean stayedWithinClickDistance;

            @SuppressLint("SetTextI18n")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ClipData.Item item = new ClipData.Item("MAP");
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData("MAP",mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder();

                event.offsetLocation(0,-66);//error compensation for difference between event of OnTouch and OnDrag

                map.setMoveStartPoint((int)event.getX(),(int)event.getY());
                textVLocation.setText("X:" + (int)event.getX() + ",Y:" + (int)event.getY() );

                v.startDrag(dragData,myShadow,null,0);
                return true;
            }
        });
        gridView.setOnDragListener(new View.OnDragListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int offsetX, offsetY;
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_LOCATION:
                        map.setMoveDestinationPoint((int)event.getX(),(int)event.getY());
                        offsetX = map.getMoveOffsetX();
                        offsetY = map.getMoveOffsetY();
                        textLocation.setText("X:" + (int)event.getX() + ",Y:" + (int)event.getY());
                        map.dragScreenByOffsets(offsetX, offsetY);
                        enqueuePositionRequest(true);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped, reassign View to ViewGroup
                        map.resetScreen();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        bagImg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // TODO
                socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("list")));
            }
        });
        zoomBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                map.zoom(progress);
                enqueuePositionRequest(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    private float pxToDp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }
}