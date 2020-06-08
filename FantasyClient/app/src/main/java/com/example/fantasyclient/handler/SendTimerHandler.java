package com.example.fantasyclient.handler;

import com.example.fantasyclient.helper.MessageSender;
import com.example.fantasyclient.helper.PositionHelper;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.PositionRequestMessage;
import com.example.fantasyclient.model.VirtualPosition;

import java.util.TimerTask;

import im.delight.android.location.SimpleLocation;

public class SendTimerHandler extends TimerHandler {

    public SendTimerHandler(final VirtualPosition vPosition, final SimpleLocation location, final MessageSender messageSender) {
        super();
        doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        location.beginUpdates();
                        PositionHelper.convertVPosition(vPosition,location.getLatitude(),location.getLongitude());
                        PositionRequestMessage p = new PositionRequestMessage(vPosition.getX(),vPosition.getY());
                        messageSender.enqueue(new MessagesC2S(p));
                    }
                });
            }
        };
    }
}
