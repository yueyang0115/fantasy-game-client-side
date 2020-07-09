package com.example.fantasyclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.MapAdapter;
import com.example.fantasyclient.adapter.MapBuildingAdapter;
import com.example.fantasyclient.adapter.MapTerritoryAdapter;
import com.example.fantasyclient.adapter.MapUnitAdapter;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private static final int TERRAIN_INIT = R.drawable.base01;
    private static final int UNIT_INIT = R.drawable.transparent;

    //fields to show map
    private MapTerritoryAdapter territoryAdapter;
    private MapUnitAdapter unitAdapter;
    private MapBuildingAdapter buildingAdapter;//Adapters for map
    private List<MapAdapter> adapterList = new ArrayList<>();
    private GridView terrainGridView, unitGridView, buildingGridView;//GridViews for map
    private WorldCoord currCoord = new WorldCoord(0,0);
    private View v;
    private int zoomLevel = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_map, container, false);
        initAdapter();
        initGridView();
        return v;
    }

    private void initAdapter(){
        Context context = getContext();
        territoryAdapter = new MapTerritoryAdapter(context, currCoord);
        unitAdapter = new MapUnitAdapter(context, currCoord);
        buildingAdapter = new MapBuildingAdapter(context, currCoord);

        adapterList.add(territoryAdapter);
        adapterList.add(unitAdapter);
        adapterList.add(buildingAdapter);

        territoryAdapter.initImage(TERRAIN_INIT);
        unitAdapter.initImage(UNIT_INIT);
        buildingAdapter.initImage(UNIT_INIT);
    }

    private void initGridView(){
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
        if(zoomLevel < 1) {
            for (MapAdapter a : adapterList) {
                a.zoom(zoomLevel);
            }
            zoomLevel++;
        }
    }

    public void zoomDown(){
        if(zoomLevel > -1) {
            for (MapAdapter a : adapterList) {
                a.zoom(zoomLevel);
            }
            zoomLevel--;
        }
    }
}
