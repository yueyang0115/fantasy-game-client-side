package com.example.fantasyclient.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.R;

import java.util.ArrayList;
import java.util.List;

public abstract class HighlightAdapter<T> extends ArrayAdapter<T> {

    int currPosition = 0;

    //View lookup cache
    static class UnitViewHolder {
        TextView unitID, unitHp, unitAtk, unitSpeed;
        ImageView unitImg;
    }

    static class InventoryViewHolder {
        TextView itemName, itemCost, itemAmount;
        NumberPicker itemNumPicker;
    }

    static class CustomViewHolder {
        TextView textView;
        ImageView imageView;
    }

    HighlightAdapter(Context context, List<T> objects) {
        super(context, 0, new ArrayList<>(objects));
    }

    public void setCurrPosition(int position){
        currPosition = position;
    }

    /**
     * This method convert image file name to image ID
     * @param ImageName
     * @return: Image ID
     */
    Drawable getDrawableByName(String ImageName){
        Resources resources = getContext().getResources();
        return resources.getDrawable(resources.getIdentifier(ImageName, "drawable", getContext().getPackageName()));
    }

    /**
     * This method distinguish specific territories from others:
     * 1. center territory
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
     * This method generate a multi-layer drawable, which is used to:
     * 1. put a green frame in the center of the map to show the current location
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
