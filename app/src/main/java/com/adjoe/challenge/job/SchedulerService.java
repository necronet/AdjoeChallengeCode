package com.adjoe.challenge.job;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.adjoe.challenge.R;
import com.adjoe.challenge.ui.MainActivity;

import java.util.Calendar;

public class SchedulerService extends JobService {

    private static final int INITIAL_DELTA = -1;
    private static final int NOTIFICATION_ID = 5;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    //TODO: this might be better to be persisted rather than keep it in memory
    private static long currentExecutionTime = -1;

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d("DEBUG","JOB HAS BEEN SCHEDULED");
        long deltaExecutionTime = INITIAL_DELTA;
        if (currentExecutionTime != -1 ){
            deltaExecutionTime = Calendar.getInstance().getTimeInMillis() - currentExecutionTime;
        }

        Log.d("DEBUG","JOB HAS BEEN SCHEDULED timelapse: "+ deltaExecutionTime);
        triggerNotification(deltaExecutionTime);

        currentExecutionTime = Calendar.getInstance().getTimeInMillis();
        jobFinished(params, true );

        return true;
    }

    private void triggerNotification(long deltaExecutionTime) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String notificationMessage;

        if(deltaExecutionTime == INITIAL_DELTA) {
            notificationMessage = getString(R.string.initial_notification_message);
        } else {
            notificationMessage =  getString(R.string.notification_message, Math.round(deltaExecutionTime/(1000*60)));
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        showNotification(builder);

    }

    private void showNotification(NotificationCompat.Builder builder) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
