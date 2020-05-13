package com.adjoe.challenge;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;

import com.adjoe.challenge.job.SchedulerService;

public class AdjoeApplication extends Application {

    private static long MINUTES = 1000 * 60 ;

    @Override
    public void onCreate() {
        super.onCreate();

        ComponentName componentName = new ComponentName(this, SchedulerService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, componentName);

        builder.setPeriodic(15 * MINUTES);

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }
}
