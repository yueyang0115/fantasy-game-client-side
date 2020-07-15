package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.SkillViewHolder;
import com.example.fantasyclient.model.Skill;

import java.util.List;

public class SkillInfoAdapter extends SkillAdapter {

    public SkillInfoAdapter(Context context, List<Skill> objects) {
        super(context, objects);
        TAG = "SkillInfoAdapter";
    }

    //set views based on the data object information
    @SuppressLint("SetTextI18n")
    @Override
    protected void setView(BaseViewHolder baseViewHolder, Skill skill , int position) {
        SkillViewHolder viewHolder = (SkillViewHolder) baseViewHolder;
        // Populate the data into the template view using the data object
        viewHolder.baseText.setText("Type: "+skill.getName());
        viewHolder.skillDamage.setText("Damage: "+skill.getAttack());
        // no need to select data object, not add frame here
        viewHolder.image.setImageDrawable(getDrawableByName(skill.getName()));
    }
}
