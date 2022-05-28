package com.example.version1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.Notification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class recent_broadcast extends BroadcastReceiver {
    SQLiteDatabase myDatabase;
    SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);
        Date currentTime;

        String year;
        String month;
        String date;
        String hours;
        String minutes;
        int day;
        float temp_percent = 0;
        float temp_goal = 0;
        float hydration_percentage;
        float goal_water = 0;
        float current_hydrate_status = 0;
        float last_updated = 100;
        float temp_date = 0;
        int temp_water = 0;

        currentTime = Calendar.getInstance().getTime();
        year = new SimpleDateFormat("yyyy").format(currentTime);
        month = new SimpleDateFormat("MM").format(currentTime);
        date = new SimpleDateFormat("dd").format(currentTime);
        hours = new SimpleDateFormat("HH").format(currentTime);
        minutes = new SimpleDateFormat("mm").format(currentTime);

        day = currentTime.getDay();












        if (checkForTableExists(context)) {
            myDatabase = context.openOrCreateDatabase("Database", context.MODE_PRIVATE, null);
            Cursor c = myDatabase.rawQuery("SELECT * FROM userdata", null);
            int goalIndex = c.getColumnIndex("goal");
            int percentIndex = c.getColumnIndex("percent");
            int dateIndex = c.getColumnIndex("date");
            int waterIndex = c.getColumnIndex("water");
            try {
                if (c != null && c.moveToFirst()) {
                    while (c != null) {
                        temp_goal = c.getInt(goalIndex);
                        temp_percent = c.getInt(percentIndex);
                        temp_date = c.getInt(dateIndex);
                        temp_water = c.getInt(waterIndex);
                        if (c.moveToNext()) { }
                        else { break; }
                    }
                    c.close();
                    myDatabase.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            hydration_percentage = temp_percent;
            if (temp_date != Integer.parseInt(date)){
                hydration_percentage = 0;
            }
            goal_water = temp_goal;
            current_hydrate_status = (hydration_percentage / 100) * temp_goal;
            //if (temp_water == 100 || temp_water == 150 || temp_water == 200 || temp_water == 300 || temp_water == 400){
                last_updated = temp_water;
            //}
            if (Integer.parseInt(date) != temp_date){
                current_hydrate_status = 0;
                hydration_percentage = 0;
            }
        }
        else {
            Log.i("TABLE------>", "DOES NOT EXISTS");
        }

        current_hydrate_status += last_updated;
        hydration_percentage = (current_hydrate_status / goal_water) * 100;

        insert_data(context, minutes, Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), (int) last_updated, (int) goal_water, (int) hydration_percentage);

        sharedPreferences.edit().putInt("last_updated", (int) last_updated);









        Notification.notificationTime(context, (Notification.calculateNextTime(context)) * 60 * 1000);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(200);

        if(sharedPreferences.getString("main_is_active", "").equals("yes")) {
            Intent mainIntent = new Intent(context.getApplicationContext(), main.class);
            context.startActivity(mainIntent);
        }
    }

    public void insert_data(Context context, String minutes, int hours, int day, int date, int month, int water, int goal, int percent){
        Log.i("INSIDE INSERT----->", "HI IAM INSIDE IT");

        sharedPreferences.edit().putString("water_log_min", minutes).apply();
        Log.i("LOGEED MINUTES---->", sharedPreferences.getString("water_log_min", ""));

        try {
            myDatabase = context.openOrCreateDatabase("Database", context.MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS userdata (hours INT(3), day INT(2), date INT(3), month INT(3), water INT(5), goal INT(5), percent INT(3), id INTEGER PRIMARY KEY)");

            myDatabase.execSQL("INSERT INTO userdata (hours, day, date, month, water, goal, percent) VALUES (" + hours + "," + day + "," + date + "," + month + "," + water + "," + goal + "," + percent + ")");
            myDatabase.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkForTableExists(Context context){
        myDatabase = context.openOrCreateDatabase("Database", context.MODE_PRIVATE, null);
        Cursor c = myDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='userdata'", null);
        if (c.getCount() > 0) {
            myDatabase.close();
            c.close();
            return true;
        }
        else {
            myDatabase.close();
            c.close();
            return false;
        }
    }
}
