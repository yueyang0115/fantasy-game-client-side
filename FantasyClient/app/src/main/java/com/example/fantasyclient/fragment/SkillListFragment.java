package com.example.fantasyclient.fragment;

import com.example.fantasyclient.adapter.SkillInfoAdapter;
import com.example.fantasyclient.model.Skill;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public class SkillListFragment extends ElementListFragment<Skill> {

    private Unit unit;
    private ElementSelector<Skill> skillSelector;

    public SkillListFragment(List<Skill> list, Unit unit, ElementSelector<Skill> skillSelector) {
        super(list);
        this.unit = unit;
        this.skillSelector = skillSelector;
    }

    @Override
    protected void initAdapter(){
        adapter = new SkillInfoAdapter(getContext(), list, unit);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener((parent, view, position, id) ->
                skillSelector.doWithSelectedElement((Skill) parent.getItemAtPosition(position)));
    }
}
