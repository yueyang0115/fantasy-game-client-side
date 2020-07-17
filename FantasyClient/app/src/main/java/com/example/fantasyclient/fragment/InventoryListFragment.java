package com.example.fantasyclient.fragment;

import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.adapter.InventoryInfoAdapter;
import com.example.fantasyclient.model.Inventory;

import java.util.List;

public class InventoryListFragment extends ElementListFragment<Inventory> {

    public InventoryListFragment(List<Inventory> list) {
        super(list);
    }

    @Override
    protected void initAdapter(){
        adapter = new InventoryInfoAdapter(getContext(), list);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> listener.doServiceFunction((SocketService socketService)->{

        }));
    }
}
