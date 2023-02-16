package com.example.fasszo;

import android.app.job.JobParameters;
import android.app.job.JobService;

//does sg like alarmreceiver, sends a notification

public class NotificationJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        new NotificationHandler(getApplicationContext())
                .send("It's time to shop something!");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) { return false; }
}
