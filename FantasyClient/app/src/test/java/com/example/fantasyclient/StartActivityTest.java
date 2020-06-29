package com.example.fantasyclient;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class StartActivityTest {

    @Test
    public void testStartActivity(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                new MockedServer();
            }
        }).start();
        ActivityScenario scenario = ActivityScenario.launch(UserLoginActivity.class);
        scenario.close();
    }
}