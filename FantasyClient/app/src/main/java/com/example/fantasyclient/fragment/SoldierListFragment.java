package com.example.fantasyclient.fragment;

import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.adapter.UnitInfoAdapter;
import com.example.fantasyclient.json.LevelUpRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public class SoldierListFragment extends ElementListFragment<Unit> {

    public SoldierListFragment(List<Unit> list) {
        super(list);
    }

    @Override
    protected void initAdapter(){
        adapter = new UnitInfoAdapter(getContext(), list);
    }

    @Override
    protected void setListener(){
        listView.setOnItemClickListener((parent, view, position, id) -> listener.doServiceFunction((SocketService socketService)->{
            Unit unit = (Unit) parent.getItemAtPosition(position);
            socketService.enqueue(new MessagesC2S(new LevelUpRequestMessage("start", unit.getId())));
        }));
    }
}
