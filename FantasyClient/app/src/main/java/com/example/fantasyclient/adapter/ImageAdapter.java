package com.example.fantasyclient.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.fantasyclient.R;

import java.util.Arrays;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    // Keep all Images in array
    private Integer[] ImageArray = {R.drawable.transparent};
    private static String TAG = "ImageHelper";
    //Change this: to have (1) current virtual coordinate
    //and (2) HashMap<VirtualCoord, Territory>



    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return ImageArray.length;
    }

    public Integer getItem(int position) {
        return ImageArray[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
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
        imageView.setImageResource(ImageArray[position]);
        return imageView;
    }

    public void initImage(int source){
        ImageArray = new Integer[150];
        Arrays.fill(ImageArray,source);
    }

    public void updateImage(int pos, int source){
        try {
            ImageArray[pos] = source;
        } catch (Exception e) {
            Log.e(TAG,"UpdateImage fails");
            e.printStackTrace();
        }
    }

    /**
     * * Updates the image at x,y if they are in range.  Otherwise do nothing.
     * @param x the x coordinate (0,0) is center of screen
     * @param y the y coordinate (0,0) is center of screen
     * @param source the image id
     */
    public void maybeUpdateImageByCoords(int x, int y, int source) {

        int pos = 64 + x - 10 * y ;
        if (pos >= 0 && pos < ImageArray.length) {
           // Log.d("ImgAdapter", "update ("+x+","+y+"=>"+pos+") to" + source);
            ImageArray[pos]  = source;
        }
        else {
        //Log.d("ImgAdapter", "ignore out of range: ("+x+","+y+"=>"+pos+") to" + source);
        }
    }
}