package com.example.fantasyclient.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private static final int WIDTH = 5;
    private static final int HEIGHT = 7;
    private static final int CENTER = 17;
    private static String TAG = "ImageAdapter";
    private Integer initImageID;
    private WorldCoord currCoord;//current virtual coordinate
    private HashMap<WorldCoord,Integer> imageMap = new HashMap<>();//HashMap<VirtualCoord, TerritoryImage>
    private List<WorldCoord> queriedCoords = new ArrayList<>();//coordinates to ask from server

    // Constructor
    public ImageAdapter(Context c, WorldCoord coord) {
        mContext = c;
        currCoord = coord;
    }

    public int getCount() {
        return WIDTH * HEIGHT;
    }

    public Integer getItem(int position) {
        int dx = position % WIDTH - WIDTH / 2;
        int dy = HEIGHT / 2 - position / WIDTH;
        return imageMap.get(new WorldCoord(dx + currCoord.getX(),dy + currCoord.getY()));

        /*WorldCoord tempCoord = new WorldCoord(dx + currCoord.getX(), dy + currCoord.getY());
        Integer imageID = imageMap.get(tempCoord);
        if(imageID == null){
            return initImageID;
        }
        else{
            return imageID;
        }*/
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        //change position to relative coordinates to center
        int dx = position % WIDTH - WIDTH / 2;
        int dy = HEIGHT / 2 - position / WIDTH;

        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        //check if current coordinate has been cached in map
        WorldCoord coord = new WorldCoord(dx + currCoord.getX(),dy + currCoord.getY());
        int imageID = initImageID;
        if(imageMap.containsKey(coord)) {
            //already cached, show the cached image
            imageID = imageMap.get(coord);
        }
        setImageByPosition(imageView,position,imageID);
        return imageView;
    }

    /**
     * This method distinguish specific territories from others:
     * 1. center territory
     * @param imageView target image view to set
     * @param position position of the image view
     * @param imageID image resource to set
     */
    private void setImageByPosition(ImageView imageView, int position, int imageID){
        if(position == CENTER){
            imageView.setImageDrawable(getCenterDrawable(imageID));
        }
        else {
            imageView.setImageResource(imageID);
        }
    }

    /**
     * This method generate a multi-layer drawable, which is used to:
     * 1. put a green frame in the center of the map to show the current location
     * @param imageID background image ID
     * @return multi-layer drawable
     */
    private LayerDrawable getCenterDrawable(int imageID){
        Resources r = mContext.getResources();
        Drawable[] layers = new Drawable[2];
        layers[0] = r.getDrawable(imageID);
        layers[1] = r.getDrawable(R.drawable.green_frame);
        return new LayerDrawable(layers);
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
        currCoord.setX(coord.getX());
        currCoord.setY(coord.getY());
        //query for the territories if not cached
        for(int i = - WIDTH / 2; i <= WIDTH / 2; i++){
            for(int j = - HEIGHT / 2; j <= HEIGHT / 2; j++){
                addQueriedCoord(new WorldCoord(currCoord.getX()+i,currCoord.getY()+j));
            }
        }
    }

    /**
     * Cache virtual coordinates into map
     * @param coord target coordinate to update
     * @param source the image id
     */
    public void updateImageByCoords(WorldCoord coord, int source) {
        imageMap.put(coord, source);
        queriedCoords.remove(coord);
    }

    private void addQueriedCoord(WorldCoord coord){
        //check if coordinate has been cached
        if(!imageMap.containsKey(coord)) {
            //coordinate not cached yet
            //check if it has been added to queryList
            if (!queriedCoords.contains(coord)) {
                queriedCoords.add(coord);
            }
        }
    }

    public List<WorldCoord> getQueriedCoords(){
        return queriedCoords;
    }
}