package com.example.fantasyclient.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.fantasyclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an ArrayAdapter class which provides a highlighted position for selecting elements in this array
 * @param <T> the type of element in array
 */
public abstract class HighlightAdapter<T> extends ArrayAdapter<T> {

    protected static String TAG;

    //target position to highlight
    int highlightedPosition = 0;

    HighlightAdapter(Context context, List<T> objects) {
        //create a new ArrayList here, otherwise, adapter.clear() will clear the input list
        super(context, 0, new ArrayList<T>(objects));
    }

    /**
     * This method sets the highlighted position,
     * which is usually called when a specific item is clicked
     * @param position
     */
    public void setHighlightedPosition(int position){
        highlightedPosition = position;
    }

    /**
     * This method convert image file name to image ID
     * @param ImageName String of image name
     * @return: Drawable
     */
    Drawable getDrawableByName(String ImageName){
        Resources resources = getContext().getResources();
        try{
            return resources.getDrawable(resources.getIdentifier(ImageName, "drawable", getContext().getPackageName()));
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Error: Resources not found");
            return null;
        }
    }

    /**
     * This method distinguish specific item from others:
     * 1. center territory
     * 2. selected item, unit, building
     * @param imageView target image view to set
     * @param position position of the image view
     * @param drawables background images
     */
    void setImageByPosition(ImageView imageView, int position, Drawable[] drawables, int currPosition){
        if(position == currPosition){
            imageView.setImageDrawable(getFrameLayerDrawable(drawables));
        }
        else {
            imageView.setImageDrawable(new LayerDrawable(drawables));
        }
    }

    /**
     * This method generate a multi-layer drawable, which puts a green frame on:
     * 1. the center of the map to show the current location
     * 2. selected item, unit, building
     * @param drawables background images
     * @return multi-layer drawable
     */
    private LayerDrawable getFrameLayerDrawable(Drawable[] drawables){
        Resources r = getContext().getResources();
        Drawable[] layers = new Drawable[drawables.length+1];
        for(int i=0; i<drawables.length; i++){
            layers[i] = drawables[i];
        }
        layers[drawables.length] = r.getDrawable(R.drawable.green_frame);
        return new LayerDrawable(layers);
    }
}
