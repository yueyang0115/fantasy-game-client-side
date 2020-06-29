package com.example.fantasyclient;

import android.content.Intent;
import android.os.IBinder;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ServiceTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class SocketServiceTest {
    @Rule
    public final ServiceTestRule serviceRule = new ServiceTestRule();

    @Test
    public void testWithBoundService() throws TimeoutException {
        // Create the service Intent.
        Intent serviceIntent =
                new Intent(ApplicationProvider.getApplicationContext(),
                        SocketService.class);

        // Data can be passed to the service via the Intent.
        //serviceIntent.putExtra(SocketService.SEED_KEY, 42L);

        // Bind the service and grab a reference to the binder.
        IBinder binder = serviceRule.bindService(serviceIntent);

        // Get the reference to the service, or you can call
        // public methods on the binder directly.
        SocketService service =
                ((SocketService.LocalBinder) binder).getService();

        // Verify that the service is working correctly.
        assertNotNull(service);
    }
}
