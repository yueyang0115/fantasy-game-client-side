package com.example.fantasyclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fantasyclient.R;

public class MenuButtonFragment extends BaseFragment {

    Button inventoryButton, soldierButton, friendButton;
    MenuButtonListener menuButtonListener;

    public interface MenuButtonListener{
        void doWithInventoryButton();
        void doWithSoldierButton();
        void doWithFriendButton();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuButtonListener) {
            menuButtonListener = (MenuButtonListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MenuButtonListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_button, container, false);
    }

    @Override
    protected void initAdapter() { }

    @Override
    protected void initView(View v) {
        inventoryButton = (Button) v.findViewById(R.id.inventoryButton);
        soldierButton = (Button) v.findViewById(R.id.soldierButton);
        friendButton = (Button) v.findViewById(R.id.friendButton);
    }

    @Override
    protected void setListener() {
        inventoryButton.setOnClickListener(v -> menuButtonListener.doWithInventoryButton());
        soldierButton.setOnClickListener(v -> menuButtonListener.doWithSoldierButton());
        friendButton.setOnClickListener(v -> menuButtonListener.doWithFriendButton());
    }
}
