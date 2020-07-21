package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fantasyclient.R;

public class BattleButtonFragment extends BaseFragment {

    Button attackButton, spellButton, inventoryButton, escapeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_battle_panel, container, false);
    }

    @Override
    protected void initAdapter() { }

    @Override
    protected void initView(View v) {
        attackButton = (Button) v.findViewById(R.id.attackButton);
        spellButton = (Button) v.findViewById(R.id.spellButton);
        inventoryButton = (Button) v.findViewById(R.id.inventoryButton);
        escapeButton = (Button) v.findViewById(R.id.escapeButton);
    }

    @Override
    protected void setListener() {
        /*attackButton.setOnClickListener(v -> listener.doServiceFunction((SocketService socketService)->{
            socketService.enqueue(new MessagesC2S(new BattleRequestMessage()));
        }));
        spellButton.setOnClickListener(v -> listener.doServiceFunction((SocketService socketService)->{
            socketService.enqueue(new MessagesC2S(new AttributeRequestMessage("list")));
        }));*/
    }
}
