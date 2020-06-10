package com.example.fantasyclient.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 15;
    private static final int CENTER = 64;
    private static String TAG = "ImageHelper";
    //Change this: to have (1) current virtual coordinate
    //and (2) HashMap<VirtualCoord, Territory>
    private Integer initImageID;
    private WorldCoord currCoord;
    private HashMap<WorldCoord,Integer> imageMap = new HashMap<>();
    private List<WorldCoord> queriedCoords = new ArrayList<>();

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return imageMap.size();
    }

    public Integer getItem(int position) {
        int dx = position % WIDTH;
        int dy = position / WIDTH;
        return imageMap.get(new WorldCoord(dx,dy));
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        int dx = position % WIDTH;
        int dy = position / WIDTH;
        //position turn it into dx + dy
        //look up current + (dx,dy) -> territory -> image -> return it
        //   if not found ? "unexplored"  (remember in a list) -> send to server (eslewhere) "TerrQueryMesg: [{x:1,y:2},{x:1,y:3},...]]
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(110, 165));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        WorldCoord coord = new WorldCoord(dx + currCoord.getX(),dy + currCoord.getY());
        Integer imageID = imageMap.get(coord);
        if(imageID!=null) {
            imageView.setImageResource(imageID);
            queriedCoords.remove(coord);
        }
        else{
            imageView.setImageResource(initImageID);
            queriedCoords.add(coord);
        }
        return imageView;
    }

    public void initImage(int source){
        initImageID = source;
    }

    public void updateCurrCoord(WorldCoord coord){
        currCoord = coord;
    }

    /**
     * * Cache virtual coordinates into map
     * @param x the x virtual coordinate
     * @param y the y virtual coordinate
     * @param source the image id
     */
    public void updateImageByCoords(int x, int y, int source) {
        imageMap.put(new WorldCoord(x, y), source);
    }

    public List<WorldCoord> getQueriedCoords(){
        return queriedCoords;
    }
}