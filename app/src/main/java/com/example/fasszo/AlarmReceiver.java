package com.example.fasszo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //creating a notification
        new NotificationHandler(context).send("It's time to shop something!");
        //that text will show if it happens
    }
}