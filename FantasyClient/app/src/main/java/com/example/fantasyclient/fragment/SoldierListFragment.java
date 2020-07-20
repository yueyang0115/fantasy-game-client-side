package com.example.fantasyclient.fragment;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.UnitInfoAdapter;
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
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Unit unit = (Unit) parent.getItemAtPosition(position);
            loadSoldierDetail(unit);
        });
    }

    public void updateSoldier(Unit unit){
        if(list.contains(unit)){
            list.get(list.indexOf(unit)).setSkills(unit.getSkills());
        }
        loadSoldierDetail(unit);
    }

    protected void loadSoldierDetail(Unit unit){
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.elementDetailLayout, new SoldierDetailFragment(unit));
        ft.commit();
    }
}
