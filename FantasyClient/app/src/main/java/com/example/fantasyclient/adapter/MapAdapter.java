package com.example.fantasyclient.adapter;

import android.content.Context;
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
    private static final int CENTER = WIDTH * HEIGHT / 2;
    private Drawable initImage;
    private WorldCoord currCoord;//current virtual coordinate
    private BidirectionalMap<WorldCoord,T> imageMap = new BidirectionalMap<WorldCoord, T>();//HashMap<VirtualCoord, TerritoryImage>
    private List<WorldCoord> queriedCoords = new ArrayList<>();//coordinates to ask from server

    // Constructor
    MapAdapter(Context context, WorldCoord coord) {
        super(context, new ArrayList<T>());
        currCoord = coord;
        highlightedPosition = CENTER;
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
        T currT;
        Drawable[] drawables;
        if(imageMap.containsKey(coord)) {
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
        currCoord.setX(coord.getX());
        currCoord.setY(coord.getY());
        //query for the territories if not cached
        updateQuery(WIDTH, HEIGHT, true);
    }

    public void updateQuery(int width, int height, boolean ifCheckMap){
        for(int i = - width / 2; i <= width / 2; i++){
            for(int j = - height / 2; j <= height / 2; j++){
                WorldCoord tempCoord = new WorldCoord(currCoord.getX() + i, currCoord.getY() + j);
                //check if coordinate has been cached
                if(ifCheckMap) {
                    checkMapThenAddQueriedCoord(tempCoord);
                }
                else{
                    addQueriedCoord(tempCoord);
                }
            }
        }
    }

    /**
     * Cache related methods
     */
    public void addToCacheByCoords(WorldCoord coord, T t) {
        imageMap.put(coord, t);
        queriedCoords.remove(coord);
    }

    public void removeFromCacheByCoords(WorldCoord coord){
        imageMap.remove(coord);
    }

    public void removeFromCacheByTarget(T t){
        imageMap.removeByValue(t);
    }

    public boolean checkCacheByCoords(WorldCoord coord){
        return imageMap.containsKey(coord);
    }

    public boolean checkCacheByTarget(T t){
        return imageMap.containsValue(t);
    }

    public T getCachedTargetByCoord(WorldCoord coord){
        return imageMap.get(coord);
    }

    public WorldCoord getCachedCoordByTarget(T t){
        return imageMap.getKey(t);
    }

    /**
     * Query related methods
     * @param coord
     */
    public void checkMapThenAddQueriedCoord(WorldCoord coord){
        if(!imageMap.containsKey(coord)) {
            //coordinate not cached yet
            addQueriedCoord(coord);
        }
    }

    public void addQueriedCoord(WorldCoord coord){
        //check if it has been added to queryList
        if (!queriedCoords.contains(coord)) {
            queriedCoords.add(coord);
        }
    }

    public List<WorldCoord> getQueriedCoords(){
        return queriedCoords;
    }
}