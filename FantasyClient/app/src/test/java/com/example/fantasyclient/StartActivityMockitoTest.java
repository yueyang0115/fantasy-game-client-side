package com.example.fantasyclient;

import android.content.Intent;
import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StartActivityMockitoTest {

    @Mock
    StartActivity mockStartActivity;

    @Test
    public void testStartActivity(){
        when(mockStartActivity.startService(new Intent(mockStartActivity, SocketService.class))).thenReturn(null);
        mockStartActivity.onCreate(new Bundle());
    }
}