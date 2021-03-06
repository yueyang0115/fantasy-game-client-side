package com.example.fantasyclient.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.fantasyclient.helper.BidirectionalMap;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.List;

public abstract class MapAdapter<T> extends HighlightAdapter<T> {
    private static final int WIDTH = 5;
    private static final int HEIGHT = 7;
    private int width = WIDTH;
    private int height = HEIGHT;
    private int center = width * height / 2;
    private int offsetX = 0;
    private int offsetY = 0;
    private int screenWidth;
    private Drawable initImage;
    private WorldCoord currCoord;//current virtual coordinate
    private BidirectionalMap<WorldCoord,T> cacheMap = new BidirectionalMap<WorldCoord, T>();//HashMap<VirtualCoord, Element>
    private List<WorldCoord> queriedCoords = new ArrayList<>();//coordinates to ask from server

    // Constructor
    MapAdapter(Context context, WorldCoord coord) {
        super(context, new ArrayList<T>());
        currCoord = coord;
        highlightedPosition = center;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    public int getCount() {
        return width * height;
    }

    public T getItem(int position) {
        int dx = position % width - width / 2;
        int dy = height / 2 - position / width;
        return cacheMap.get(new WorldCoord(dx + currCoord.getX(),dy + currCoord.getY()));
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        //change position to relative coordinates to center
        int dx = position % width - width / 2 - offsetX;
        int dy = height / 2 - position / width + offsetY;

        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(screenWidth / width, screenWidth / width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        }
        else {
            imageView = (ImageView) convertView;
        }

        //check if current coordinate has been cached in map
        WorldCoord coord = new WorldCoord(dx + currCoord.getX(),dy + currCoord.getY());
        T currT;
        Drawable[] drawables;
        if(cacheMap.containsKey(coord)) {
            //already cached, show the cached image
            currT = getCachedTargetByCoord(coord);
            drawables = getImageDrawables(imageView, position, currT);
        }
        else{

            drawables = new Drawable[]{initImage};
        }

        setImageByPosition(imageView, position, drawables);
        return imageView;
    }

    protected abstract Drawable[] getImageDrawables(ImageView imageView, int position, T t);

    /**
     * Set the initial image of this adapter
     * @param source image ID
     */
    public void initImage(int source){
        initImage = getContext().getResources().getDrawable(source);
    }

    /**
     * This method update current coordinate when players move
     * @param coord current coordinate
     */
    public void updateCurrCoord(WorldCoord coord){
        currCoord.setByCoord(coord);
        //query for the territories if not cached
        updateQueryByMapSize();
    }

    public void updateQueryByMapSize(){
        updateQueryByWidthAndHeight(width, height, true);
    }

    /**
     * This method add required coordinates to query list based on:
     * @param width
     * @param height
     * @param ifCheckMap if it need to check the map
     *                   true: when players change location, same territory will not be queried again
     *                   false: when battle ends, tame of cached territories will change
     */
    public void updateQueryByWidthAndHeight(int width, int height, boolean ifCheckMap){
        for(int i = - width / 2; i <= width / 2; i++){
            for(int j = - height / 2; j <= height / 2; j++){
                WorldCoord tempCoord = new WorldCoord(currCoord.getX() + i - offsetX, currCoord.getY() + j + offsetY);
                if(ifCheckMap) {
                    //need to check if coordinate has been cached
                    checkMapThenAddQueriedCoord(tempCoord);
                }
                else{
                    //no need to check map, add to query directly
                    addQueriedCoord(tempCoord);
                }
            }
        }
    }

    /**
     * Cache related methods
     */
    public void addToCacheByCoords(WorldCoord coord, T t) {
        cacheMap.put(coord, t);
        queriedCoords.remove(coord);
    }

    public void removeFromCacheByCoords(WorldCoord coord){
        cacheMap.remove(coord);
    }

    public void removeFromCacheByTarget(T t){
        cacheMap.removeByValue(t);
    }

    public boolean checkCacheByCoords(WorldCoord coord){
        return cacheMap.containsKey(coord);
    }

    public boolean checkCacheByTarget(T t){
        return cacheMap.containsValue(t);
    }

    public T getCachedTargetByCoord(WorldCoord coord){
        return cacheMap.get(coord);
    }

    public WorldCoord getCachedCoordByTarget(T t){
        return cacheMap.getKey(t);
    }

    public void clearCache(){
        cacheMap.clear();
    }

    /**
     * Check map first, only add to query when target coordinate has not been cached before
     * @param coord
     */
    private void checkMapThenAddQueriedCoord(WorldCoord coord){
        if(!cacheMap.containsKey(coord)) {
            //coordinate not cached yet
            addQueriedCoord(coord);
        }
    }

    /**
     * ask for specific territories and add to query without check
     * @param coord
     */
    private void addQueriedCoord(WorldCoord coord){
        //check if it has been added to queryList
        if (!queriedCoords.contains(coord)) {
            queriedCoords.add(coord);
        }
    }

    public List<WorldCoord> getQueriedCoords(){
        return queriedCoords;
    }

    /**
     * zoom up and down
     */
    public void zoom(int zoomLevel){
        width = WIDTH + 2 * zoomLevel;
        height = HEIGHT + 2 * zoomLevel;
        center = width * height / 2;
        highlightedPosition = center;
        updateQueryByWidthAndHeight(width, height, true);
    }

    public int getNumColumn(){
        return width;
    }

    public int getCenter(){
        return center;
    }

    public int getColumnWidth(){
        return screenWidth/width;
    }

    /**
     * Drag screen
     */
    public void updateOffset(int offsetX, int offsetY){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        updateQueryByWidthAndHeight(width, height, true);
    }
}