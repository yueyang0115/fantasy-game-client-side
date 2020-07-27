package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.PlayerInfoViewHolder;
import com.example.fantasyclient.model.PlayerInfo;

import java.util.List;

public class PlayerInfoAdapter extends ElementAdapter<PlayerInfo> {

    //initialize the skill layout
    public PlayerInfoAdapter(Context context, List<PlayerInfo> objects) {
        super(context, objects, R.layout.element_playerinfo);
    }

    @Override
    protected BaseViewHolder getViewHolder() {
        return new PlayerInfoViewHolder();
    }

    @Override
    protected void findView(BaseViewHolder baseViewHolder, View convertView) {
        PlayerInfoViewHolder viewHolder = (PlayerInfoViewHolder) baseViewHolder;
        viewHolder.baseText = (TextView) convertView.findViewById(R.id.playerID);
        viewHolder.username = (TextView) convertView.findViewById(R.id.playerUsername);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.playerImg);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setView(BaseViewHolder baseViewHolder, PlayerInfo playerInfo, int position) {
        PlayerInfoViewHolder viewHolder = (PlayerInfoViewHolder) baseViewHolder;
        // Populate the data into the template view using the data object
        viewHolder.baseText.setText("ID: "+ playerInfo.getId());
        viewHolder.username.setText("Damage: "+ playerInfo.getUsername());
    }
}
