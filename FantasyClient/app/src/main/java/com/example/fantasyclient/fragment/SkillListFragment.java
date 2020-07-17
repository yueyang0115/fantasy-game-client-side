package com.example.fantasyclient.fragment;

import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.adapter.SkillInfoAdapter;
import com.example.fantasyclient.model.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SkillListFragment extends ElementListFragment<Skill> {

    public SkillListFragment(List<Skill> list) {
        super(list);
    }

    public SkillListFragment(Set<Skill> set) {
        super(new ArrayList<>(set));
    }

    @Override
    protected void initAdapter(){
        adapter = new SkillInfoAdapter(getContext(), list);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> listener.doServiceFunction((SocketService socketService) -> {

        }));
    }
}
