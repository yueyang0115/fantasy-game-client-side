package com.example.fantasyclient.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected ActivityWithService activityListener;
    protected String TAG;

    /**
     * This method stores touched activity as listener
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivityWithService) {
            activityListener = (ActivityWithService) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ActivityWithService");
        }
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        initAdapter();
        initView(view);
        setListener();
    }

    protected abstract void initAdapter();

    protected abstract void initView(View v);

    protected abstract void setListener();

    /**
     * This method convert image file name to image ID
     * @param ImageName String of image name
     * @return: Drawable
     */
    protected Drawable getDrawableByName(String ImageName){
        String resourceName = ImageName;
        Resources resources = requireContext().getResources();
        String packageName = requireContext().getPackageName();
        int identifier = resources.getIdentifier(ImageName,"string", packageName);
        if(identifier!=0){
            resourceName = resources.getString(identifier);
        }
        try{
            return resources.getDrawable(resources.getIdentifier(resourceName, "drawable", packageName));
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Error: Resources not found");
            return null;
        }
    }
}
