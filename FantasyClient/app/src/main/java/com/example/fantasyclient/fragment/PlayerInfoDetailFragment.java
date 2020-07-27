package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fantasyclient.R;
import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.adapter.PlayerInfoAdapter;
import com.example.fantasyclient.json.FriendRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.model.PlayerInfo;

public class PlayerInfoDetailFragment extends ElementDetailFragment<PlayerInfo> {

    Button buttonApply;

    public PlayerInfoDetailFragment(PlayerInfo playerInfo) {
        super(playerInfo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playerinfo_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initAdapter(){
        adapter = new PlayerInfoAdapter(getContext(), list);
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        //PlayerInfo playerInfo = list.get(0);
        buttonApply = (Button) v.findViewById(R.id.buttonApply);
    }

    @Override
    protected void setListener(){
        buttonApply.setOnClickListener(v -> {
            activityListener.doWithServiceFunction((SocketService socketService)->{
                socketService.enqueue(new MessagesC2S(new FriendRequestMessage(FriendRequestMessage.ActionType.apply,list.get(0).getId())));
            });
        });
    }

}
