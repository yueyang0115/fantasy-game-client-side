package com.example.fantasyclient.helper;

import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.PositionRequestMessage;
import com.example.fantasyclient.model.VirtualPosition;

import java.util.TimerTask;

public class SendTimerHandler extends TimerHandler {

    public SendTimerHandler(final VirtualPosition vPosition, final MessageSender messageSender) {
        super();
        doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        PositionRequestMessage p = new PositionRequestMessage(vPosition.getX(),vPosition.getY());
                        messageSender.enqueue(new MessagesC2S(p));
                    }
                });
            }
        };
    }
}
