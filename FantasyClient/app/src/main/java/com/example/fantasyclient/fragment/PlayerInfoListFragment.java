package com.example.fantasyclient.fragment;

import com.example.fantasyclient.adapter.PlayerInfoAdapter;
import com.example.fantasyclient.model.PlayerInfo;

import java.util.List;

public class PlayerInfoListFragment extends ElementListFragment<PlayerInfo> {

    protected PlayerSelector playerSelector;

    public interface PlayerSelector {
        void doWithSelectedPlayer(PlayerInfo playerInfo);
    }

    public PlayerInfoListFragment(List<PlayerInfo> list, PlayerSelector playerSelector) {
        super(list);
        this.playerSelector = playerSelector;
    }

    @Override
    protected void initAdapter(){
        adapter = new PlayerInfoAdapter(getContext(), list);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            adapter.setHighlightedPosition(position);
            updateAdapter(adapter, list);
            playerSelector.doWithSelectedPlayer((PlayerInfo) parent.getItemAtPosition(position));
        });
    }
}
