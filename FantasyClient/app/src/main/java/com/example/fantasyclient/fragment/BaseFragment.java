package com.example.fantasyclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected ActivityWithService activityListener;

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
}
