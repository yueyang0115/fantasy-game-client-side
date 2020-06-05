package com.example.fantasyclient.helper;

import android.util.Log;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.PositionRequestMessage;
import com.example.fantasyclient.model.VirtualPosition;

import java.util.TimerTask;

import im.delight.android.location.SimpleLocation;

public class LocationTimerHandler extends TimerHandler {

    public LocationTimerHandler(final VirtualPosition vPosition, final SimpleLocation location) {
        super();
        doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        location.beginUpdates();
                        PositionHelper.convertVPosition(vPosition,location.getLatitude(),location.getLongitude());
                        Log.d("location",location.getLatitude()+","+location.getLongitude());
                    }
                });
            }
        };
    }
}
