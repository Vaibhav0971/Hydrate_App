package com.example.version1;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.Notification;

import java.net.URI;

public class notification_broadcast extends BroadcastReceiver {



    @Override
    public void onReceive(final Context context, Intent intent) {

        //String further;
        int mode;
        int sound;
        //String remainder_schedule;
        //String remainder_schedule_min;
        //String remainder_schedule_hrs;
        String channel_id;

        Uri soundUri = null;

        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);

        //further = sharedPreferences.getString("further", "");
        mode = Integer.parseInt(sharedPreferences.getString("mode", ""));
        sound = Integer.parseInt(sharedPreferences.getString("sound", ""));
        //remainder_schedule = sharedPreferences.getString("remainder_schedule", "");
        //remainder_schedule_min = sharedPreferences.getString("remainder_schedule_min", "45");
        //remainder_schedule_hrs = sharedPreferences.getString("remainder_schedule_hrs", "0");
        channel_id = sharedPreferences.getString("channel_id", "");


        if (mode != 5) {

            Intent notificationIntent = new Intent(context.getApplicationContext(), main.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 1, notificationIntent, 0);

            Intent snoozeIntent = new Intent(context.getApplicationContext(), snooze_broadcast.class);
            PendingIntent snooze_pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, snoozeIntent, 0);

            /*
            Intent customIntent = new Intent(context.getApplicationContext(), custom_broadcast.class);
            PendingIntent custom_pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, customIntent, 0);

            Intent recentIntent = new Intent(context.getApplicationContext(), recent_broadcast.class);
            PendingIntent recent_pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, recentIntent, 0);
             */

            if (0 < sound && sound < 7) {
                if (sound == 1) {
                    soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.water_1);
                    sharedPreferences.edit().putString("sound", "1").apply();
                }
                if (sound == 2) {
                    soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.water_2);
                    sharedPreferences.edit().putString("sound", "2").apply();
                }
                if (sound == 3) {
                    soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.water_3);
                    sharedPreferences.edit().putString("sound", "3").apply();
                }
                if (sound == 4) {
                    soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.water_4);
                    sharedPreferences.edit().putString("sound", "4").apply();
                }
                if (sound == 5) {
                    soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.water_5);
                    sharedPreferences.edit().putString("sound", "5").apply();
                }
                if (sound == 6) {
                    soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.water_6);
                    sharedPreferences.edit().putString("sound", "6").apply();
                }
            }
            else {
                soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.water_3);
                sharedPreferences.edit().putString("sound", "3").apply();
            }


            if (mode == 1) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                        .setSmallIcon(R.drawable.drop)
                        .setContentTitle("Time to get hydrated")
                        .setContentText("Please have a glass of water")
                        .setAutoCancel(true)
                        .setLights(Color.BLUE, 500, 500)
                        .setVibrate(new long[]{500, 500, 500, 500})
                        .setSound(soundUri)
                        .addAction(R.drawable.drop, "snooze", snooze_pendingIntent)
                        //.addAction(R.drawable.drop, "custom", custom_pendingIntent)
                        //.addAction(R.drawable.drop, "recent", recent_pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                builder.setContentIntent(pendingIntent);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(200, builder.build());
            }

            if (mode == 2) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                        .setSmallIcon(R.drawable.drop)
                        .setContentTitle("Time to get hydrated")
                        .setContentText("Please have a glass of water")
                        .setAutoCancel(true)
                        .setLights(Color.BLUE, 500, 500)
                        .setSound(soundUri)
                        .addAction(R.drawable.drop, "snooze", snooze_pendingIntent)
                        //.addAction(R.drawable.drop, "custom", custom_pendingIntent)
                        //.addAction(R.drawable.drop, "recent", recent_pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                builder.setContentIntent(pendingIntent);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(200, builder.build());
            }

            if (mode == 3) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                        .setSmallIcon(R.drawable.drop)
                        .setContentTitle("Time to get hydrated")
                        .setContentText("Please have a glass of water")
                        .setAutoCancel(true)
                        .setLights(Color.BLUE, 500, 500)
                        .setVibrate(new long[]{500, 500, 500, 500})
                        .addAction(R.drawable.drop, "snooze", snooze_pendingIntent)
                        //.addAction(R.drawable.drop, "custom", custom_pendingIntent)
                        //.addAction(R.drawable.drop, "recent", recent_pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                builder.setContentIntent(pendingIntent);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(200, builder.build());
            }

            if (mode == 4) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                        .setSmallIcon(R.drawable.drop)
                        .setContentTitle("Time to get hydrated")
                        .setContentText("Please have a glass of water")
                        .setAutoCancel(true)
                        .setLights(Color.BLUE, 500, 500)
                        .addAction(R.drawable.drop, "snooze", snooze_pendingIntent)
                        //.addAction(R.drawable.drop, "custom", custom_pendingIntent)
                        //.addAction(R.drawable.drop, "recent", recent_pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                builder.setContentIntent(pendingIntent);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(200, builder.build());
            }


            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            //boolean isScreenOn = pm.isScreenOn();
            //Log.i("Screen status---------", String.valueOf(isScreenOn));
            if (!(pm.isScreenOn())) {
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
                wl.acquire(10000);
                PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");
                wl_cpu.acquire(10000);
            }

            //Toast.makeText(context.getApplicationContext(), Long.toString(intent.getLongExtra("time", 0)), Toast.LENGTH_SHORT).show();
            //MediaPlayer mp= MediaPlayer.create(context, R.raw.water_3);
            //mp.start();

            sharedPreferences.edit().putString("future_notification_time_set", "no").apply();
            //sharedPreferences.edit().putString("snooze_pressed", "no").apply();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Notification.notificationTime(context, (Notification.calculateNextTime(context)) * 60 * 1000);
                    //Toast.makeText(context.getApplicationContext(), "Hi 1min & 10sec has passed", Toast.LENGTH_SHORT).show();
                }
            },1000 * 70);

        }
    }
}



