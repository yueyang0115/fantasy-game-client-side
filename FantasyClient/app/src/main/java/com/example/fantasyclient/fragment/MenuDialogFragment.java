package com.example.fantasyclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.fantasyclient.R;

public class MenuDialogFragment extends DialogFragment {

    //activity which contains this fragment
    private OnMenuListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        Button inventoryButton = (Button) v.findViewById(R.id.inventoryButton);
        Button soldierButton = (Button) v.findViewById(R.id.soldierButton);
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInventorySelected();
            }
        });
        soldierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSoldierSelected();
            }
        });
        return v;
    }

    /**
     * This is an interface for activity to implement
     * to realize data communication between activity and fragment
     */
    public interface OnMenuListener {
        void onSoldierSelected();
        void onInventorySelected();
    }

    /**
     * This method stores touched activity as listener
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuListener) {
            listener = (OnMenuListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MenuDialogFragment.OnMenuListener");
        }
    }
}
