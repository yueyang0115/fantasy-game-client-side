package com.example.fantasyclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.MapBuildingAdapter;
import com.example.fantasyclient.adapter.MapTerritoryAdapter;
import com.example.fantasyclient.adapter.MapUnitAdapter;
import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.Monster;
import com.example.fantasyclient.model.Territory;
import com.example.fantasyclient.model.WorldCoord;

import java.util.List;

public class MapFragment extends Fragment {

    private static final int TERRAIN_INIT = R.drawable.base01;
    private static final int UNIT_INIT = R.drawable.transparent;

    //fields to show map
    private MapTerritoryAdapter territoryAdapter;
    private MapUnitAdapter unitAdapter;
    private MapBuildingAdapter buildingAdapter;//Adapters for map
    private GridView terrainGridView, unitGridView, buildingGridView;//GridViews for map
    private WorldCoord currCoord = new WorldCoord(0,0);
    private int zoomLevel = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        initAdapter();
        initGridView(view);
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

    private void initGridView(View v){
        terrainGridView = (GridView) v.findViewById(R.id.terrainGridView);
        unitGridView = (GridView) v.findViewById(R.id.unitGridView);
        buildingGridView = (GridView) v.findViewById(R.id.buildingGridView);

        terrainGridView.setAdapter(territoryAdapter);
        unitGridView.setAdapter(unitAdapter);
        buildingGridView.setAdapter(buildingAdapter);
    }

    public GridView getClickableGridView(){
        return unitGridView;
    }

    public void zoomUp(){
        if(zoomLevel<1) {
            int tempZoomLevel = zoomLevel + 1;
            zoom(tempZoomLevel);
            zoomLevel = tempZoomLevel;
        }
    }

    public void zoomDown(){
        if(zoomLevel > -1) {
            int tempZoomLevel = zoomLevel + 1;
            zoom(tempZoomLevel);
            zoomLevel = tempZoomLevel;
        }
    }

    private void zoom(int zoomLevel){
        territoryAdapter.zoom(zoomLevel);
        unitAdapter.zoom(zoomLevel);
        buildingAdapter.zoom(zoomLevel);
        terrainGridView.setNumColumns(territoryAdapter.getWIDTH());
        unitGridView.setNumColumns(unitAdapter.getWIDTH());
        buildingGridView.setNumColumns(buildingAdapter.getWIDTH());
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

    public boolean checkUnitCacheByCoord(WorldCoord coord){
        return unitAdapter.checkCacheByCoords(coord);
    }

    public boolean checkBuildingCacheByCoord(WorldCoord coord){
        return buildingAdapter.checkCacheByCoords(coord);
    }

    public Territory getTerritoryByPosition(int position){
        return territoryAdapter.getItem(position);
    }

    public Building getBuildingByPosition(int position){
        return buildingAdapter.getItem(position);
    }
}
