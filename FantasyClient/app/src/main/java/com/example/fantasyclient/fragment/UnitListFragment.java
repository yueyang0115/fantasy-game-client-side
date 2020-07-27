package com.example.fantasyclient.fragment;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.UnitInfoAdapter;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public class UnitListFragment extends ElementListFragment<Unit> {

    UnitSelector unitSelector;

    public UnitListFragment(List<Unit> list, UnitSelector unitSelector) {
        super(list);
        this.unitSelector = unitSelector;
    }

    public interface UnitSelector{
        void doWithSelectedUnit(Unit unit);
    }

    @Override
    protected void initAdapter(){
        adapter = new UnitInfoAdapter(getContext(), list);
    }

    @Override
    protected void setListener(){
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Unit unit = (Unit) parent.getItemAtPosition(position);
            if(unit.getHp()>0) {
                adapter.setHighlightedPosition(position);
                updateAdapter(adapter, list);
            }
            unitSelector.doWithSelectedUnit(unit);
        });
    }

    public void loadSoldierDetail(Unit unit){
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.elementDetailLayout, new UnitDetailFragment(unit));
        ft.commit();
    }

    @Override
    public Unit getSelectedElement(){
        Unit unit = super.getSelectedElement();
        if(unit.getHp()<=0){
            for(int i=0; i<list.size(); i++){
                unit = list.get(i);
                if(unit.getHp()>0){
                    adapter.setHighlightedPosition(i);
                    updateAdapter(adapter, list);
                    break;
                }
            }
        }
        return unit;
    }
}
