package com.example.fantasyclient.fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.MapBuildingAdapter;
import com.example.fantasyclient.adapter.MapTerritoryAdapter;
import com.example.fantasyclient.adapter.MapUnitAdapter;
import com.example.fantasyclient.helper.MapMoveTool;
import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.Monster;
import com.example.fantasyclient.model.Territory;
import com.example.fantasyclient.model.Unit;
import com.example.fantasyclient.model.WorldCoord;

import java.util.List;

public class MapFragment extends Fragment {

    private static final int TERRAIN_INIT = R.drawable.base01;
    private static final int UNIT_INIT = R.drawable.transparent;

    private WorldCoord currCoord;
    private MapMoveTool mapMoveTool = new MapMoveTool();

    //fields to show map
    private MapTerritoryAdapter territoryAdapter;
    private MapUnitAdapter unitAdapter;
    private MapBuildingAdapter buildingAdapter;//Adapters for map
    private GridView terrainGridView, unitGridView, buildingGridView;//GridViews for map

    //activity which contains this fragment
    private OnMapSelectedListener listener;


    public MapFragment(WorldCoord currCoord) {
        this.currCoord = currCoord;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    /**
     * This is an interface for activity to implement
     * to realize data communication between activity and fragment
     */
    public interface OnMapSelectedListener {
        void onMapClick(int position);
        void onMapLongClick(int position);
        void onMapDrag();
    }

    /**
     * This method stores parent activity as listener
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMapSelectedListener) {
            listener = (OnMapSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MapFragment.OnMapItemClickListener");
        }
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        initAdapter();
        initView(view);
        setOnClickListener();
    }

    private void initAdapter(){
        Context context = getContext();
        territoryAdapter = new MapTerritoryAdapter(context, currCoord);
        unitAdapter = new MapUnitAdapter(context, currCoord);
        buildingAdapter = new MapBuildingAdapter(context, currCoord);

        territoryAdapter.initImage(TERRAIN_INIT);
        unitAdapter.initImage(UNIT_INIT);
        buildingAdapter.initImage(UNIT_INIT);
    }

    private void initView(View v){
        terrainGridView = (GridView) v.findViewById(R.id.terrainGridView);
        unitGridView = (GridView) v.findViewById(R.id.unitGridView);
        buildingGridView = (GridView) v.findViewById(R.id.buildingGridView);

        terrainGridView.setAdapter(territoryAdapter);
        unitGridView.setAdapter(unitAdapter);
        buildingGridView.setAdapter(buildingAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnClickListener(){
        getClickableGridView().setOnTouchListener(new MapOnTouchListener());
        getClickableGridView().setOnDragListener(new MapOnDragListener());
    }

    public GridView getClickableGridView(){
        return unitGridView;
    }

    public void zoom(int zoomLevel){
        territoryAdapter.zoom(zoomLevel);
        unitAdapter.zoom(zoomLevel);
        buildingAdapter.zoom(zoomLevel);
        terrainGridView.setNumColumns(territoryAdapter.getNumColumn());
        unitGridView.setNumColumns(unitAdapter.getNumColumn());
        buildingGridView.setNumColumns(buildingAdapter.getNumColumn());
        mapMoveTool.setAmplificationFactor(zoomLevel);
    }

    public void dragScreenByOffsets(int offsetX, int offsetY){
        territoryAdapter.updateOffset(offsetX, offsetY);
        unitAdapter.updateOffset(offsetX, offsetY);
        buildingAdapter.updateOffset(offsetX, offsetY);
        updateMapLayers();
    }

    public void resetScreen(){
        territoryAdapter.updateOffset(0, 0);
        unitAdapter.updateOffset(0, 0);
        buildingAdapter.updateOffset(0, 0);
        updateMapLayers();
    }

    public List<WorldCoord> getQueriedCoords(){
        return territoryAdapter.getQueriedCoords();
    }

    /**
     * This method changes the source file array in several adapters for corresponding map layers
     * mainly including layers that are unlikely to change: terrain, building
     * UI will be updated when adapter.notifyDataSetChanged() is called on UI thread
     * @param t: target territory
     */
    public void updateTerrain(Territory t){
        WorldCoord targetCoord = t.getCoord();
        //update terrain layer
        territoryAdapter.addToCacheByCoords(targetCoord,t);
    }

    /**
     * This method changes unit adapters with the following steps:
     * 1. Check if the unit with this id has been cached before, if so, remove it
     * 2. Update its new coordinate and add it the cache
     */
    public void updateMonster(Monster m){
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
    public void updateBuilding(Building b){
        buildingAdapter.addToCacheByCoords(b.getCoord(),b);
    }

    public void updateMapLayers(){
        territoryAdapter.notifyDataSetChanged();
        unitAdapter.notifyDataSetChanged();
        buildingAdapter.notifyDataSetChanged();
    }

    public void updateCurrCoord(WorldCoord coord){
        territoryAdapter.updateCurrCoord(coord);
        unitAdapter.updateCurrCoord(coord);
        buildingAdapter.updateCurrCoord(coord);
    }

    public void updateTerritoryQueryAfterBattle(){
        territoryAdapter.updateQuery(3, 3, false);
    }

    public void removeUnitByCoordAfterBattle(WorldCoord coord){
        unitAdapter.removeFromCacheByCoords(coord);
    }

    /**
     * Check cache map by coordinates
     * @param coord
     * @return
     */
    public boolean checkUnitCacheByCoord(WorldCoord coord){
        return unitAdapter.checkCacheByCoords(coord);
    }

    public boolean checkBuildingCacheByCoord(WorldCoord coord){
        return buildingAdapter.checkCacheByCoords(coord);
    }


    /**
     * Get elements of specific position
     * @param position
     * @return
     */
    public Territory getTerritoryByPosition(int position){
        return territoryAdapter.getItem(position);
    }

    public Building getBuildingByPosition(int position){
        return buildingAdapter.getItem(position);
    }

    public Unit getUnitByPosition(int position){
        return unitAdapter.getItem(position);
    }

    /**
     * MapMoveTool related methods
     */
    public void setMoveStartPoint(float startX, float startY){
        mapMoveTool.setStartX(startX);
        mapMoveTool.setStartY(startY);
    }

    public void setMoveDestinationPoint(float destX, float destY){
        mapMoveTool.setDestX(destX);
        mapMoveTool.setDestY(destY);
    }

    public int getMoveOffsetX(){
        return mapMoveTool.getOffsetX();
    }

    public int getMoveOffsetY(){
        return mapMoveTool.getOffsetY();
    }

    public int getColumnWidth(){
        return territoryAdapter.getColumnWidth();
    }

    public int getColumnNum(){
        return territoryAdapter.getNumColumn();
    }

    public int getCenter(){
        return territoryAdapter.getCenter();
    }

    public boolean ifStayedWithinClickDistance(float density){
        return mapMoveTool.ifStayedWithinClickDistance(density);
    }

    /**
     * This method convert the touch point location (dp) to position of GridView (int) on map
     * @param x of touch point (dp)
     * @param y of touch point (dp)
     * @return position of GridView (int) on map
     */
    public int dpToPosition(float x, float y){
        int columnWidth = getColumnWidth();
        int column = (int) x / columnWidth;
        int row = (int) y / columnWidth;
        return row * getColumnNum() + column;
    }

    private class MapOnTouchListener implements View.OnTouchListener{

        //Max allowed duration for a "click", in milliseconds.
        private static final int MAX_CLICK_DURATION = 500;
        private long pressStartTime;
        private float startX;
        private float startY;
        private boolean stayedWithinClickDistance;

        @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    //cache start point and time of touch
                    pressStartTime = System.currentTimeMillis();
                    startX = event.getX();
                    startY = event.getY();
                    stayedWithinClickDistance = true;
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    setMoveStartPoint(startX,startY);
                    if(stayedWithinClickDistance && !ifStayedWithinClickDistance(getResources().getDisplayMetrics().density)) {
                        ClipData.Item item = new ClipData.Item("MAP");
                        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                        ClipData dragData = new ClipData("MAP",mimeTypes, item);
                        event.offsetLocation(0,-66);//error compensation for difference between event of OnTouch and OnDrag
                        stayedWithinClickDistance = false;
                        v.startDrag(dragData,new View.DragShadowBuilder(),null,0);
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    long pressDuration = System.currentTimeMillis() - pressStartTime;
                    //check drag distance to differentiate drag and click
                    if(stayedWithinClickDistance) {
                        int position =  dpToPosition(startX, startY);
                        //check press duration time to differentiate click and long click
                        if (pressDuration < MAX_CLICK_DURATION) {
                            // Click event has occurred
                            listener.onMapClick(position);
                        }
                        else{
                            // Long click event has occurred
                            listener.onMapLongClick(position);
                        }
                    }
                    break;
                }
            }
            return false;
        }
    }

    private class MapOnDragListener implements View.OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_LOCATION:
                    setMoveDestinationPoint((int)event.getX(),(int)event.getY());
                    dragScreenByOffsets(getMoveOffsetX(), getMoveOffsetY());
                    listener.onMapDrag();
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    resetScreen();
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
