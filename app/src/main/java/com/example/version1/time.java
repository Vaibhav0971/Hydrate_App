package com.example.version1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class time extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TimePicker wakeUpTime;
    TimePicker sleepTime;






    @RequiresApi(api = Build.VERSION_CODES.M)
    public void next(View view){

        //Date currentTime;
        Date currentTime = Calendar.getInstance().getTime();

        sharedPreferences.edit().putString("start_hrs", new SimpleDateFormat("HH").format(currentTime)).apply();
        sharedPreferences.edit().putString("start_min", new SimpleDateFormat("mm").format(currentTime)).apply();


        sharedPreferences.edit().putString("wh", Integer.toString(wakeUpTime.getHour())).apply();
        sharedPreferences.edit().putString("wm", Integer.toString(wakeUpTime.getMinute())).apply();
        sharedPreferences.edit().putString("sh", Integer.toString(sleepTime.getHour())).apply();
        sharedPreferences.edit().putString("sm", Integer.toString(sleepTime.getMinute())).apply();
        sharedPreferences.edit().putString("further", "no").apply();
        sharedPreferences.edit().putString("mode", "1").apply();
        sharedPreferences.edit().putString("sound", "1").apply();
        sharedPreferences.edit().putString("remainder_schedule", "1").apply();
        sharedPreferences.edit().putString("remainder_schedule_min", "45").apply();
        sharedPreferences.edit().putString("remainder_schedule_hrs", "0").apply();
        sharedPreferences.edit().putString("channel_id", "1").apply();
        sharedPreferences.edit().putString("userDataAcquired", "true").apply();


        //Toast.makeText(getApplicationContext(), sharedPreferences.getString("wh", "") + ">>" + sharedPreferences.getString("wm", "") + ">>" + sharedPreferences.getString("sh", "") + ">>" + sharedPreferences.getString("sm", ""), Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(time.this, main.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen


        setContentView(R.layout.time);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPreferences = this.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);

        wakeUpTime = findViewById(R.id.wTime);
        wakeUpTime.setIs24HourView(true);

        sleepTime = findViewById(R.id.sTime);
        sleepTime.setIs24HourView(true);

        wakeUpTime.setHour(6);
        wakeUpTime.setMinute(0);
        sleepTime.setHour(22);
        sleepTime.setMinute(0);

/*
        String weight = sharedPreferences.getString("weight", "");
        String unit = sharedPreferences.getString("unit", "");

 */









    }
}
