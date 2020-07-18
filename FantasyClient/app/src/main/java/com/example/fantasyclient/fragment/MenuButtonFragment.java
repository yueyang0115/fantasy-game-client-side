package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fantasyclient.R;
import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.json.AttributeRequestMessage;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;

public class MenuButtonFragment extends BaseFragment {

    Button inventoryButton, soldierButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    protected void initAdapter() { }

    @Override
    protected void initView(View v) {
        inventoryButton = (Button) v.findViewById(R.id.inventoryButton);
        soldierButton = (Button) v.findViewById(R.id.soldierButton);
    }

    @Override
    protected void setListener() {
        inventoryButton.setOnClickListener(v -> listener.doServiceFunction((SocketService socketService)->{
            socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("list")));
        }));
        soldierButton.setOnClickListener(v -> listener.doServiceFunction((SocketService socketService)->{
            socketService.enqueue(new MessagesC2S(new AttributeRequestMessage("list")));
        }));
    }
}
