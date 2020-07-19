package com.example.fantasyclient.fragment;

import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.adapter.SkillInfoAdapter;
import com.example.fantasyclient.json.LevelUpRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.model.Skill;
import com.example.fantasyclient.model.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SkillListFragment extends ElementListFragment<Skill> {

    private Unit unit;

    public SkillListFragment(List<Skill> list, Unit unit) {
        super(list);
        this.unit = unit;
    }

    public SkillListFragment(Set<Skill> set, Unit unit) {
        super(new ArrayList<>(set));
        this.unit = unit;
    }

    @Override
    protected void initAdapter(){
        adapter = new SkillInfoAdapter(getContext(), list, unit);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> listener.doServiceFunction((SocketService socketService) -> {
            Skill skill = (Skill) parent.getItemAtPosition(position);
            socketService.enqueue(new MessagesC2S(new LevelUpRequestMessage("choose", unit.getId(), skill)));
        }));
    }
}
