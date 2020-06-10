package com.example.fantasyclient.handler;

import com.example.fantasyclient.adapter.ImageAdapter;
import com.example.fantasyclient.helper.MessageSender;
import com.example.fantasyclient.helper.PositionHelper;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.PositionRequestMessage;
import com.example.fantasyclient.model.VirtualPosition;
import com.example.fantasyclient.model.WorldCoord;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import im.delight.android.location.SimpleLocation;

public class SendTimerHandler extends TimerHandler {

    public SendTimerHandler(final WorldCoord currCoord, final SimpleLocation location, final List<ImageAdapter> adapterList, final MessageSender messageSender) {
        super();
        doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        location.beginUpdates();
                        PositionHelper.convertVPosition(currCoord,location.getLatitude(),location.getLongitude());
                        for(ImageAdapter adapter : adapterList){
                            adapter.updateCurrCoord(currCoord);
                        }
                        List<WorldCoord> queriedCoords = adapterList.get(0).getQueriedCoords();
                        PositionRequestMessage p = new PositionRequestMessage(queriedCoords); //add to this message: a list of coordinates you want information for
                        messageSender.enqueue(new MessagesC2S(p));
                    }
                });
            }
        };
    }
}
