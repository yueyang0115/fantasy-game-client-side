package com.example.fantasyclient.helper;

import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;

public class TimerHandler {
    private Timer timer;
    TimerTask doAsyncTask;
    Handler handler;

    TimerHandler() {
        Looper.prepare();
        handler = new Handler();
        timer = new Timer();
    }

    public void handleTask(int delay, int period){
        timer.schedule(doAsyncTask, delay, period); //execute in every period
        Looper.loop();
    }

    public void cancelTask(){
        timer.cancel();
    }
}
