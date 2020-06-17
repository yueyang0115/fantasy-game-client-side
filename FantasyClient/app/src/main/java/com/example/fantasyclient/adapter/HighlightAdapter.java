package com.example.fantasyclient.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.fantasyclient.R;

import java.util.ArrayList;
import java.util.List;

public abstract class HighlightAdapter<T> extends ArrayAdapter<T> {

    protected int currPosition = 0;

    public HighlightAdapter(Context context, List<T> objects) {
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
    protected int getImageID(String ImageName){
        return getContext().getResources().getIdentifier(ImageName, "drawable", getContext().getPackageName());
    }

    /**
     * This method distinguish specific territories from others:
     * 1. center territory
     * @param imageView target image view to set
     * @param position position of the image view
     * @param imageID image resource to set
     */
    protected void setImageByPosition(ImageView imageView, int position, int imageID, int currPosition){
        if(position == currPosition){
            imageView.setImageDrawable(getCenterDrawable(getContext(), imageID));
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
    private LayerDrawable getCenterDrawable(Context context, int imageID){
        Resources r = context.getResources();
        Drawable[] layers = new Drawable[2];
        layers[0] = r.getDrawable(imageID);
        layers[1] = r.getDrawable(R.drawable.green_frame);
        return new LayerDrawable(layers);
    }
}
