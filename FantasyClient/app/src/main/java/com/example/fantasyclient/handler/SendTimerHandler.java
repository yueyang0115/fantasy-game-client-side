package com.example.fantasyclient.handler;

import com.example.fantasyclient.adapter.ImageAdapter;
import com.example.fantasyclient.helper.MessageSender;
import com.example.fantasyclient.helper.PositionHelper;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.json.PositionRequestMessage;
import com.example.fantasyclient.model.WorldCoord;

import java.util.List;
import java.util.TimerTask;

import im.delight.android.location.SimpleLocation;

/**
 * This class update current location and send coordinates queries to server periodically
 */
public class SendTimerHandler extends TimerHandler {

    public SendTimerHandler(final WorldCoord currCoord, final SimpleLocation location, final List<ImageAdapter> adapterList, final MessageSender messageSender) {
        super();
        doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //update current location
                        location.beginUpdates();
                        //convert location data to virtual coordinate
                        PositionHelper.convertVPosition(currCoord,location.getLatitude(),location.getLongitude());
                        //update current coordinate of all layers of map
                        for(ImageAdapter adapter : adapterList){
                            adapter.updateCurrCoord(currCoord);
                        }
                        //get the coordinates which need to be queried from server
                        List<WorldCoord> queriedCoords = adapterList.get(0).getQueriedCoords();
                        //enqueue query message in order to send to server
                        PositionRequestMessage p = new PositionRequestMessage(queriedCoords); //add to this message: a list of coordinates you want information for
                        messageSender.enqueue(new MessagesC2S(p));
                    }
                });
            }
        };
    }
}
