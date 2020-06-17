package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapAdapter<T> extends HighlightAdapter<T> {
    private static final int WIDTH = 5;
    private static final int HEIGHT = 7;
    private static final int CENTER = WIDTH * HEIGHT / 2;
    private Integer initImageID;
    private WorldCoord currCoord;//current virtual coordinate
    HashMap<WorldCoord,T> imageMap = new HashMap<>();//HashMap<VirtualCoord, TerritoryImage>
    private List<WorldCoord> queriedCoords = new ArrayList<>();//coordinates to ask from server

    // Constructor
    public MapAdapter(Context context, WorldCoord coord) {
        super(context, new ArrayList<T>());
        currCoord = coord;
    }

    public int getCount() {
        return WIDTH * HEIGHT;
    }

    public T getItem(int position) {
        int dx = position % WIDTH - WIDTH / 2;
        int dy = HEIGHT / 2 - position / WIDTH;
        return imageMap.get(new WorldCoord(dx + currCoord.getX(),dy + currCoord.getY()));
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
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(1100/WIDTH, 1100/WIDTH));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        }
        else {
            imageView = (ImageView) convertView;
        }

        //check if current coordinate has been cached in map
        WorldCoord coord = new WorldCoord(dx + currCoord.getX(),dy + currCoord.getY());
        int imageID = initImageID;
        if(imageMap.containsKey(coord)) {
            //already cached, show the cached image
            imageID = getCachedImageID(coord);
        }
        setImageByPosition(imageView, position, imageID, CENTER);
        return imageView;
    }

    protected int getCachedImageID(WorldCoord coord){
        return 0;
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
     * @param t target territory
     */
    public void addToCacheByCoords(WorldCoord coord, T t) {
        imageMap.put(coord, t);
        queriedCoords.remove(coord);
    }

    public void removeFromCacheByCoords(WorldCoord coord){
        imageMap.remove(coord);
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