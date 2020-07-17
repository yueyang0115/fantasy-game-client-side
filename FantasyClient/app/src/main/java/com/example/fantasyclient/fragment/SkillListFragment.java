package com.example.fantasyclient.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.example.fantasyclient.adapter.SkillInfoAdapter;
import com.example.fantasyclient.model.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SkillListFragment extends ElementListFragment<Skill> {

    //activity which contains this fragment
    protected OnSkillSelectedListener listener;

    public SkillListFragment(List<Skill> list) {
        super(list);
    }

    public SkillListFragment(Set<Skill> set) {
        super(new ArrayList<>(set));
    }

    /**
     * This is an interface for activity to implement
     * to realize data communication between activity and fragment
     */
    public interface OnSkillSelectedListener {
        void onSkillSelected(Skill skill);
    }

    /**
     * This method stores touched activity as listener
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSkillSelectedListener) {
            listener = (OnSkillSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement InventoryListFragment.OnInventorySelectedListener");
        }
    }

    @Override
    protected void initAdapter(){
        adapter = new SkillInfoAdapter(getContext(), list);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onSkillSelected((Skill) parent.getItemAtPosition(position));
            }
        });
    }
}
