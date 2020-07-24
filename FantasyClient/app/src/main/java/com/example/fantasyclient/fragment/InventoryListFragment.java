package com.example.fantasyclient.fragment;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.R;
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
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Inventory inventory = (Inventory) parent.getItemAtPosition(position);
            loadTargetList(inventory);
        });
    }

    protected void loadTargetList(Inventory inventory){
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.elementDetailLayout, new InventoryDetailFragment(inventory));
        ft.commit();
    }
}
