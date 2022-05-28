package com.example.version1;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.Notification;

public class snooze_broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);

        Toast.makeText(context.getApplicationContext(), "Next remainder in " + Notification.calculateNextTime(context) + " min" , Toast.LENGTH_SHORT).show();

        sharedPreferences.edit().putString("snooze_pressed", "yes").apply();

        Notification.notificationTime(context, (Notification.calculateNextTime(context)) * 60 * 1000);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(200);

        if(sharedPreferences.getString("main_is_active", "").equals("yes")) {
            Intent mainIntent = new Intent(context.getApplicationContext(), main.class);
            context.startActivity(mainIntent);
        }
    }
}
