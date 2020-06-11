package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 15;
    private static final int CENTER = 64;
    private static String TAG = "ImageHelper";
    private Integer initImageID;
    private WorldCoord currCoord;//current virtual coordinate
    private HashMap<WorldCoord,Integer> imageMap = new HashMap<>();//HashMap<VirtualCoord, TerritoryImage>
    private List<WorldCoord> queriedCoords = new ArrayList<>();//coordinates to ask from server

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

        //change position to relative coordinates to center
        int dx = position % WIDTH;
        int dy = position / WIDTH;

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

        //check if current coordinate has been cached in map
        WorldCoord coord = new WorldCoord(dx + currCoord.getX(),dy + currCoord.getY());
        Integer imageID = imageMap.get(coord);
        if(imageID!=null) {
            //already cached, no need to query again
            imageView.setImageResource(imageID);
            queriedCoords.remove(coord);
        }
        else{
            //not cached yet, need to query
            imageMap.put(coord,initImageID);
            imageView.setImageResource(initImageID);
            queriedCoords.add(coord);
        }
        return imageView;
    }

    /**
     * Set the initial image of this adapter
     * @param source image ID
     */
    public void initImage(int source){
        initImageID = source;
    }

    /**
     * This method update current coordinate when players move
     * @param coord current coordinate
     */
    public void updateCurrCoord(WorldCoord coord){
        currCoord = coord;
        //check if it is the begin of a game
        if(imageMap.size()==0){
            //no cached map, query for the whole screen
            for(int i=-4; i<=5; i++){
                for(int j=-8; j<=6; j++){
                    queriedCoords.add(new WorldCoord(currCoord.getX()+i,currCoord.getY()+j));
                }
            }
        }
    }

    /**
     * Cache virtual coordinates into map
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