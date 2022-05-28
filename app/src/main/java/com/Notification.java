package com;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.version1.R;
import com.example.version1.main;
import com.example.version1.notification_broadcast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Notification extends AppCompatActivity {

    //#1
    ////////////////////////////////////////////////////////////////////////////////////////////
    //CREATE NOTIFICATION CHANNEL

    public static void createNotificationChannel(Context context, String channelId, int sound, int mode){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            //Log.i("CHANNEL ID-------", channelId);
            //Log.i("SOUND-------", String.valueOf(sound));
            //Log.i("MODE-------", String.valueOf(mode));
            if (mode != 5) {

                CharSequence name = "Hydrate Notification";
                String description = "Channel for app notification";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                Uri soundUri = null;
                SharedPreferences sharedPreferences;
                sharedPreferences = context.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);

                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                //Log.i("CHANNELS BEFORE-------", String.valueOf(notificationManager.getNotificationChannels()));

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


                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();

                NotificationChannel channel = new NotificationChannel(channelId, name, importance);
                channel.setDescription(description);
                if (mode == 1 || mode == 2) {
                    channel.setSound(soundUri, audioAttributes);
                }
                if (mode == 3 || mode == 4){
                    channel.setSound(null, null);
                }
                channel.enableLights(true);
                channel.setLightColor(Color.BLUE);
                if (mode == 1 || mode == 3) {
                    channel.enableVibration(true);
                }
                channel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PUBLIC);

                notificationManager.createNotificationChannel(channel);
            }
        }
    }


    //#2
    ////////////////////////////////////////////////////////////////////////////////////////////
    //NOTIFICATION TIME


    public static void notificationTime(Context context, long time){
        //9876 is the secret time code to stop notification
        //Log.i("TIME VALUE-------->","" + time);
        if (time != (987 * 60 *1000)) {
            Intent intent = new Intent(context, notification_broadcast.class);
            //intent.putExtra("time", time);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            long currentTimeMillis = System.currentTimeMillis();
            alarmManager.set(AlarmManager.RTC_WAKEUP, currentTimeMillis + time, pendingIntent);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    //DELETE NOTIFICATION CHANNEL


    public static void deleteNotificationChannel(Context context, String channelId){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.deleteNotificationChannel(channelId);
        }
    }


    //#3
    ////////////////////////////////////////////////////////////////////////////////////////////
    //CALCULATE TIME FOR NEXT NOTIFICATION

    public static int calculateNextTime(Context context) {

        SQLiteDatabase myDatabase;
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);

        Date currentTime;
        String current_hours;
        String current_minutes;
        String last_water_log_min;
        String last_water_log_hour = null;
        String current_amORpm = null;
        String last_amORpm = null;
        String remainder_schedule_hrs;
        String remainder_schedule_min;
        String remainder_snooze_hrs;
        String remainder_snooze_min;
        //String snooze_pressed;
        String future_notification_time;
        //String future_notification_time_set;
        int time_remaining_for_notification = 0;
        int hours = 0;
        int mins = 0;
        int time_interval;
        int snooze_interval;
        int temp_percent = 0;
        int wake_up_gap;
        long mills = 0;
        long time1 = 0;
        long time2 = 0;
        long time3 = 0;
        boolean proceed;


        currentTime = Calendar.getInstance().getTime();
        current_hours = new SimpleDateFormat("HH").format(currentTime);
        current_minutes = new SimpleDateFormat("mm").format(currentTime);
        last_water_log_min = sharedPreferences.getString("water_log_min", "");


        remainder_schedule_hrs = sharedPreferences.getString("remainder_schedule_hrs", "0");
        remainder_schedule_min = sharedPreferences.getString("remainder_schedule_min", "45");
        remainder_snooze_hrs = sharedPreferences.getString("remainder_snooze_hrs", "0");
        remainder_snooze_min = sharedPreferences.getString("remainder_snooze_min", "20");
        //snooze_pressed = sharedPreferences.getString("snooze_pressed", "");
        //future_notification_time = sharedPreferences.getString("future_notification_time", "");
        //future_notification_time_set = sharedPreferences.getString("future_notification_time_set", "");


        if ((Integer.parseInt(current_hours) * 60) + Integer.parseInt(current_minutes) >= (Integer.parseInt(sharedPreferences.getString("wh", "")) * 60) + Integer.parseInt(sharedPreferences.getString("wm", "")) && (Integer.parseInt(current_hours) * 60) + Integer.parseInt(current_minutes) < (Integer.parseInt(sharedPreferences.getString("sh", "")) * 60) + Integer.parseInt(sharedPreferences.getString("sm", ""))) {

            Log.i("BETWEEN TIME ALLOT", "INSIDE IT");

            myDatabase = context.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            Cursor c = myDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='userdata'", null);
            if (c.getCount() > 0) {
                //Log.i("TABLE------>", "EXISTS");
                c = myDatabase.rawQuery("SELECT * FROM userdata", null);
                int hoursIndex = c.getColumnIndex("hours");
                int percentIndex = c.getColumnIndex("percent");
                try {
                    if (c != null && c.moveToFirst()) {
                        while (c != null) {
                            //Log.i("hour------>", c.getString(hoursIndex));
                            last_water_log_hour = c.getString(hoursIndex);
                            temp_percent = c.getInt(percentIndex);
                            if (c.moveToNext()) { }
                            else { break; }
                        }
                        c.close();
                        myDatabase.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Log.i("WATER LOG HOUR---->", last_water_log_hour);
            }
            else {
                myDatabase.close();
                c.close();
                last_water_log_hour = sharedPreferences.getString("start_hrs", "12");
                last_water_log_min = sharedPreferences.getString("start_min", "59");
                //return ((Integer.parseInt(remainder_schedule_hrs) * 60) + Integer.parseInt(remainder_schedule_min));
            }


            /*
            Log.i("WATER LOG HOUR---->", last_water_log_hour);
            Log.i("WATER LOG MINUTES---->", last_water_log_min);
            Log.i("CURRENT HOURS---->", current_hours);
            Log.i("CURRENT MINMUTES---->", current_minutes);
             */



            if (temp_percent >= 100 && Integer.parseInt(sharedPreferences.getString("further", "")) == 1) {
                proceed = true;
            }
            else {
                proceed = false;
            }
            if (temp_percent < 100){
                proceed = true;
            }

            Log.i("TEMP PERCENTAGE------>", Integer.toString(temp_percent));

            if (proceed){

                Log.i("PERCENTAGE AND FURTHER", "INSIDE IT");

                if (Integer.parseInt(current_hours) < 12) {
                    current_amORpm = "am";
                }
                else if (12 <= Integer.parseInt(current_hours) && Integer.parseInt(current_hours) <= 24) {
                    current_amORpm = "pm";
                }
                if (Integer.parseInt(last_water_log_hour) < 12) {
                    last_amORpm = "am";
                }
                else if (12 <= Integer.parseInt(last_water_log_hour) && Integer.parseInt(last_water_log_hour) <= 24) {
                    last_amORpm = "pm";
                }


                try {
                    SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss aa");
                    Date date1 = format.parse(current_hours + ":" + current_minutes + ":00 " + current_amORpm);
                    Date date2 = format.parse(last_water_log_hour + ":" + last_water_log_min + ":00 " + last_amORpm);
                    //Date date3 = format.parse("23:59:00 pm");
                    mills = date1.getTime() - date2.getTime();
                    hours = (int) (mills / (1000 * 60 * 60));
                    mins = (int) (mills / (1000 * 60)) % 60;

                    if (hours < 0) {
                        hours = ((hours * 60) + (24 * 60) + mins) / 60;
                        mins = ((hours * 60) + (24 * 60) + mins) % 60;
                    }
                    /*
                    Log.i("YESYESYES--------->", "*****************************************");
                    Log.i("Date1--------->", String.valueOf(date1));
                    Log.i("Date2--------->", String.valueOf(date2));
                    Log.i("Date1 getvalue----->", String.valueOf(date1.getTime()));
                    Log.i("Date2 getvalue----->", String.valueOf(date2.getTime()));
                    Log.i("Mills--------->", String.valueOf(mills));
                    Log.i("hours--------->", String.valueOf(hours));
                    Log.i("mins--------->", String.valueOf(mins));
                    */
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                time_interval = (Integer.parseInt(remainder_schedule_hrs) * 60) + Integer.parseInt(remainder_schedule_min);
                snooze_interval = (Integer.parseInt(remainder_snooze_hrs) * 60) + Integer.parseInt(remainder_snooze_min);

                Log.i("HOURS------------>", String.valueOf(hours));
                Log.i("MINS------------->", String.valueOf(mins));

                if (time_interval < ((hours * 60) + mins)) {
                    Log.i("TIME---------->", "TIME INTERVAL IS LESS THAN HOURS -------↓↓↓↓");
                    if (sharedPreferences.getString("snooze_pressed", "").equals("yes")) {
                        Log.i("SNOOZE PRESSED------>", "----------SNOOZE PRESSED------");
                        sharedPreferences.edit().putString("future_notification_time", Integer.toString(snooze_interval + (Integer.parseInt(current_hours) * 60) + Integer.parseInt(current_minutes))).apply();
                        sharedPreferences.edit().putString("future_notification_time_set", "yes").apply();
                        sharedPreferences.edit().putString("snooze_pressed", "no").apply();
                    }
                    if (sharedPreferences.getString("future_notification_time_set", "").equals("no")) {
                        sharedPreferences.edit().putString("future_notification_time", Integer.toString(time_interval + (Integer.parseInt(current_hours) * 60) + Integer.parseInt(current_minutes))).apply();
                        sharedPreferences.edit().putString("future_notification_time_set", "yes").apply();
                    }
                    future_notification_time = sharedPreferences.getString("future_notification_time", "");
                    time_remaining_for_notification = Integer.parseInt(future_notification_time) - (Integer.parseInt(current_hours) * 60) - Integer.parseInt(current_minutes);
                }
                if (time_interval >= ((hours * 60) + mins)) {
                    Log.i("TIME----------;)", "TIME INTERVAL IS MORE THAN HOURS -------↑↑↑↑");
                    time_remaining_for_notification = time_interval - ((hours * 60) + mins);
                    sharedPreferences.edit().putString("future_notification_time_set", "no").apply();
                }

                Log.i("TIME REMAINING----->", String.valueOf(time_remaining_for_notification));

                if (((Integer.parseInt(current_hours) * 60) + Integer.parseInt(current_minutes) + time_remaining_for_notification) > ((Integer.parseInt(sharedPreferences.getString("sh", "")) * 60) + Integer.parseInt(sharedPreferences.getString("sm", "")))){
                    //SAME CODE AS THE ELSE WHEN TIME IS NOT IN THE USER'S WAKEUP TIME RANGE

                    if (Integer.parseInt(current_hours) < 12) {
                        current_amORpm = "am";
                    } else if (12 <= Integer.parseInt(current_hours) && Integer.parseInt(current_hours) <= 24) {
                        current_amORpm = "pm";
                    }
                    time1 = (Integer.parseInt(current_hours) * 60) + Integer.parseInt(current_minutes);
                    time2 = (23 * 60) + 59;
                    time3 = (Integer.parseInt(sharedPreferences.getString("wh", "")) * 60) + Integer.parseInt(sharedPreferences.getString("wm", ""));
                    if (current_amORpm.equals("pm")) {
                        mills = (time2 - time1) + time3;
                    }
                    if (current_amORpm.equals("am")) {
                        mills = time3 - time1;
                    }
                    hours = (int) (mills / 60);
                    mins = (int) (mills % 60);
                    wake_up_gap = 40;
                    time_remaining_for_notification = (hours * 60) + mins + wake_up_gap;
                    return time_remaining_for_notification;

                }
                else {
                    return time_remaining_for_notification;
                }
            }
            else {
                return 987;//9876 is the secret time code to stop notification
            }
        }
        else {
            if (Integer.parseInt(current_hours) < 12) {
                current_amORpm = "am";
            } else if (12 <= Integer.parseInt(current_hours) && Integer.parseInt(current_hours) <= 24) {
                current_amORpm = "pm";
            }

            /*
            if (Integer.parseInt(sharedPreferences.getString("wh", "")) < 12) {
                last_amORpm = "am";
            } else if (12 <= Integer.parseInt(sharedPreferences.getString("wh", "")) && Integer.parseInt(sharedPreferences.getString("wh", "")) <= 24) {
                last_amORpm = "pm";
            }
            */

            time1 = (Integer.parseInt(current_hours) * 60) + Integer.parseInt(current_minutes);
            time2 = (23 * 60) + 59;
            time3 = (Integer.parseInt(sharedPreferences.getString("wh", "")) * 60) + Integer.parseInt(sharedPreferences.getString("wm", ""));

            Log.i("Time1------->", String.valueOf(time1));
            Log.i("Time2------->", String.valueOf(time2));
            Log.i("Time3------->", String.valueOf(time3));

            if (current_amORpm.equals("pm")) {
                mills = (time2 - time1) + time3;
            }
            if (current_amORpm.equals("am")) {
                mills = time3 - time1;
            }

            Log.i("Mills------->", String.valueOf(mills));

            hours = (int) (mills / 60);
            mins = (int) (mills % 60);

            Log.i("Hours------->", String.valueOf(hours));
            Log.i("Mins------->", String.valueOf(mins));

            wake_up_gap = 40;

            time_remaining_for_notification = (hours * 60) + mins + wake_up_gap;

            //Toast.makeText(context.getApplicationContext(), "TRFN " + time_remaining_for_notification + " min" , Toast.LENGTH_SHORT).show();

            return time_remaining_for_notification;
        }


/*
        if (current_amORpm.equals("am") && last_amORpm.equals("pm") && (Integer.parseInt(current_hours) * 60) + Integer.parseInt(current_minutes) <= (Integer.parseInt(sharedPreferences.getString("wh", "")) * 60) + Integer.parseInt(sharedPreferences.getString("wm", "")) && (Integer.parseInt(current_hours) * 60) + Integer.parseInt(current_minutes) < (Integer.parseInt(sharedPreferences.getString("sh", "")) * 60) + Integer.parseInt(sharedPreferences.getString("sm", ""))) {

        }
 */


    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    //CALCULATE TIME FOR HYDRATED TEXT

    public static String[] lastDrinkTime(Context context){

        SQLiteDatabase myDatabase;
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);

        Date currentTime;
        String current_hours;
        String current_minutes;
        String last_water_log_min;
        String last_water_log_hour = null;
        String current_amORpm = null;
        String last_amORpm = null;
        String lastDrinkTime[] = new String[2];
        int hours = 0;
        int mins = 0;

        myDatabase = context.openOrCreateDatabase("Database", MODE_PRIVATE, null);
        Cursor c = myDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='userdata'", null);
        if (c.getCount() > 0) {
            //Log.i("TABLE------>", "EXISTS");
            c = myDatabase.rawQuery("SELECT * FROM userdata", null);
            int hoursIndex = c.getColumnIndex("hours");
            try {
                if (c != null && c.moveToFirst()) {
                    while (c != null) {
                        //Log.i("hour------>", c.getString(hoursIndex));
                        last_water_log_hour = c.getString(hoursIndex);
                        if (c.moveToNext()) { }
                        else { break; }
                    }
                    c.close();
                    myDatabase.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Log.i("WATER LOG HOUR---->", last_water_log_hour);
        }
        else {
            myDatabase.close();
            c.close();
            lastDrinkTime[0] = Integer.toString(0);
            lastDrinkTime[1] = Integer.toString(0);
            Log.i("LAST DRINK TIME----->", lastDrinkTime[0] + "hrs and " + lastDrinkTime[1] + "min");
            return lastDrinkTime;
        }

        currentTime = Calendar.getInstance().getTime();
        current_hours = new SimpleDateFormat("HH").format(currentTime);
        current_minutes = new SimpleDateFormat("mm").format(currentTime);
        last_water_log_min = sharedPreferences.getString("water_log_min", "");

        /*
        Log.i("WATER LOG HOUR---->", last_water_log_hour);
        Log.i("WATER LOG MINUTES---->", last_water_log_min);
        Log.i("CURRENT HOURS---->", current_hours);
        Log.i("CURRENT MINMUTES---->", current_minutes);
         */

        if (Integer.parseInt(current_hours) < 12){
            current_amORpm = "am";
        }
        else if (12 <= Integer.parseInt(current_hours) && Integer.parseInt(current_hours) <= 24){
            current_amORpm = "pm";
        }
        if (Integer.parseInt(last_water_log_hour) < 12){
            last_amORpm = "am";
        }
        else if (12 <= Integer.parseInt(last_water_log_hour) && Integer.parseInt(last_water_log_hour) <= 24){
            last_amORpm = "pm";
        }


        try
        {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
            Date date1 = format.parse(current_hours + ":" + current_minutes + ":00 "+ current_amORpm);
            Date date2 = format.parse(last_water_log_hour + ":" + last_water_log_min + ":00 "+ last_amORpm);
            long mills = date1.getTime() - date2.getTime();
            hours = (int) (mills/(1000 * 60 * 60));
            mins = (int) (mills/(1000*60)) % 60;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        lastDrinkTime[0] = Integer.toString(hours);
        lastDrinkTime[1] = Integer.toString(mins);
        Log.i("LAST DRINK TIME----->", lastDrinkTime[0] + "hrs and " + lastDrinkTime[1] + "min");
        return lastDrinkTime;
    }





    ////////////////////////////////////////////////////////////////////////////////////////////

}
