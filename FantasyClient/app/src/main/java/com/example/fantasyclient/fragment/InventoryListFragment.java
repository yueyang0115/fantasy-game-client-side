package com.example.fantasyclient.fragment;

import com.example.fantasyclient.adapter.InventoryInfoAdapter;
import com.example.fantasyclient.model.Inventory;

import java.util.List;

public class InventoryListFragment extends ElementListFragment<Inventory> {

    private InventorySelector inventorySelector;

    public InventoryListFragment(List<Inventory> list, InventorySelector inventorySelector) {
        super(list);
        this.inventorySelector = inventorySelector;
    }

    public interface InventorySelector{
        void doWithSelectedInventory(Inventory inventory);
    }

    @Override
    protected void initAdapter(){
        adapter = new InventoryInfoAdapter(getContext(), list);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Inventory inventory = (Inventory) parent.getItemAtPosition(position);
            adapter.setHighlightedPosition(position);
            updateAdapter(adapter, list);
            inventorySelector.doWithSelectedInventory(inventory);
        });
    }
}
