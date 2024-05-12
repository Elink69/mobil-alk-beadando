package com.gzn1ev.aramlejelentes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String CHANNEL_ID = "1";
    private NotificationManager manager;
    private Context context;

    public NotificationHandler(Context context){
        this.context = context;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        setChannel();
    }

    private void setChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Áram lejelentés", NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableVibration(true);
        channel.setDescription("Áram lejelentés értesítés");
        this.manager.createNotificationChannel(channel);
    }

    public void send(String message){
        Intent intent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Áram lejelentés")
                .setContentText(message)
                .setSmallIcon(R.drawable.receipt_long)
                .setContentIntent(pendingIntent);

        this.manager.notify(1, builder.build());
    }
}
