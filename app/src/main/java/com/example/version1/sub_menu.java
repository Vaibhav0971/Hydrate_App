package com.example.version1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.Notification;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class sub_menu extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SQLiteDatabase myDatabase;
    TextView heading;
    Date currentTime = new Date();
    TimePickerDialog timePickerDialog;
    MediaPlayer mplayer;
    AudioManager audioManager;
    NumberPicker hour_picker;
    NumberPicker minute_picker;
    LinearLayout alert_layout;

    ImageView bg_1;
    ImageView bg_2;
    ImageView bg_3;
    ImageView bg_4;
    ImageView bg_5;
    ImageView bg_6;
    ImageView bg_7;

    TextView t_1;
    TextView t_2;
    TextView t_3;
    TextView t_4;
    TextView t_5;
    TextView t_6;
    TextView t_7;

    TextView rt_1;
    TextView rt_2;
    TextView rt_3;
    TextView rt_4;

    CheckBox ct_1;
    CheckBox ct_2;
    CheckBox ct_3;
    CheckBox ct_4;
    CheckBox ct_5;
    CheckBox ct_6;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLE DECLARE

    String year;
    String month;
    String date;
    String hours;

    String button_name;

    String temp_remainder_schedule_hrs;
    String temp_remainder_schedule_min;
    String temp_snooze_hrs;
    String temp_snooze_min;

    int temp_goal;
    int day;
    float temp_percent;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //SHARED PREFERENCE VARIABLE DECLARE

    String gender;
    String weight;
    String unit;
    String wh;
    String wm;
    String sh;
    String sm;
    String further;
    String mode;
    String sound;
    String remainder_schedule_hrs;
    String remainder_schedule_min;
    String remainder_snooze_hrs;
    String remainder_snooze_min;
    String channel_id;
    String userDataAcquired;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ONBACKPRESS

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(sub_menu.this, menu.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mode != sharedPreferences.getString("mode", "") || sound != sharedPreferences.getString("sound", "")) {
            Notification.deleteNotificationChannel(getApplicationContext(), channel_id);
            sharedPreferences.edit().putString("channel_id", String.valueOf(Integer.parseInt(sharedPreferences.getString("channel_id", "")) + 1)).apply();
            Notification.createNotificationChannel(getApplicationContext(), sharedPreferences.getString("channel_id", ""), Integer.parseInt(sharedPreferences.getString("sound", "")), Integer.parseInt(sharedPreferences.getString("mode", "")));
            Notification.notificationTime(getApplicationContext(), (Notification.calculateNextTime(getApplicationContext())) * 60 * 1000);
        }


        for (int x=0; x<100; x++) {
            Log.i("SUB PAUSE----------", String.valueOf(x));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mode != sharedPreferences.getString("mode", "") || sound != sharedPreferences.getString("sound", "")) {
            Notification.deleteNotificationChannel(getApplicationContext(), channel_id);
            sharedPreferences.edit().putString("channel_id", String.valueOf(Integer.parseInt(sharedPreferences.getString("channel_id", "")) + 1)).apply();
            Notification.createNotificationChannel(getApplicationContext(), sharedPreferences.getString("channel_id", ""), Integer.parseInt(sharedPreferences.getString("sound", "")), Integer.parseInt(sharedPreferences.getString("mode", "")));
            Notification.notificationTime(getApplicationContext(), (Notification.calculateNextTime(getApplicationContext())) * 60 * 1000);
        }


        for (int x=0; x<100; x++) {
            Log.i("SUB DESTROYED----------", String.valueOf(x));
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ONCREATE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.sub_menu);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //SCREEN SETTING

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //INITIALIZE

        sharedPreferences = this.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);
        heading = findViewById(R.id.heading);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        hour_picker = new NumberPicker(getApplicationContext());
        minute_picker = new NumberPicker(getApplicationContext());
        alert_layout = new LinearLayout(getApplicationContext());

        bg_1 = findViewById(R.id.bg_1);
        bg_2 = findViewById(R.id.bg_2);
        bg_3 = findViewById(R.id.bg_3);
        bg_4 = findViewById(R.id.bg_4);
        bg_5 = findViewById(R.id.bg_5);
        bg_6 = findViewById(R.id.bg_6);
        bg_7 = findViewById(R.id.bg_7);
        t_1 = findViewById(R.id.t_1);
        t_2 = findViewById(R.id.t_2);
        t_3 = findViewById(R.id.t_3);
        t_4 = findViewById(R.id.t_4);
        t_5 = findViewById(R.id.t_5);
        t_6 = findViewById(R.id.t_6);
        t_7 = findViewById(R.id.t_7);
        rt_1 = findViewById(R.id.rt_1);
        rt_2 = findViewById(R.id.rt_2);
        rt_3 = findViewById(R.id.rt_3);
        rt_4 = findViewById(R.id.rt_4);
        ct_1 = findViewById(R.id.ct_1);
        ct_2 = findViewById(R.id.ct_2);
        ct_3 = findViewById(R.id.ct_3);
        ct_4 = findViewById(R.id.ct_4);
        ct_5 = findViewById(R.id.ct_5);
        ct_6 = findViewById(R.id.ct_6);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //VARIABLE INITIALIZE

        try {
            year = new SimpleDateFormat("yyyy").format(currentTime);
            month = new SimpleDateFormat("MM").format(currentTime);
            date = new SimpleDateFormat("dd").format(currentTime);
            hours = new SimpleDateFormat("HH").format(currentTime);
            day = currentTime.getDay();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Log.i("YEAR----->", year);
        Log.i("MONTH----->", month);
        Log.i("DATE----->", date);
        Log.i("HOURS----->", hours);
        Log.i("DAY----->", String.valueOf(day));

        gender = sharedPreferences.getString("gender", "");
        weight = sharedPreferences.getString("weight", "");
        unit = sharedPreferences.getString("unit", "");
        wh = sharedPreferences.getString("wh", "");
        wm = sharedPreferences.getString("wm", "");
        sh = sharedPreferences.getString("sh", "");
        sm = sharedPreferences.getString("sm", "");
        further = sharedPreferences.getString("further", "");
        mode = sharedPreferences.getString("mode", "");
        sound = sharedPreferences.getString("sound", "");
        remainder_schedule_hrs = sharedPreferences.getString("remainder_schedule_hrs", "0");
        remainder_schedule_min = sharedPreferences.getString("remainder_schedule_min", "45");
        remainder_snooze_hrs = sharedPreferences.getString("remainder_snooze_hrs", "0");
        remainder_snooze_min = sharedPreferences.getString("remainder_snooze_min", "20");
        channel_id = sharedPreferences.getString("channel_id", "");
        userDataAcquired = sharedPreferences.getString("userDataAcquired", "");

        button_name = getIntent().getStringExtra("button_name");


        ////////////////////////////////////////////////////////////////////////////////////////////
        //FETCHING DATA FROM DATABASE


        if (checkForTableExists()) {
            Log.i("TABLE------>", "EXISTS");
            myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            Cursor c = myDatabase.rawQuery("SELECT * FROM userdata", null);
            int goalIndex = c.getColumnIndex("goal");
            int percentIndex = c.getColumnIndex("percent");
            try {
                if (c != null && c.moveToFirst()) {
                    while (c != null) {

                        temp_goal = c.getInt(goalIndex);
                        temp_percent = c.getInt(percentIndex);
                        c.moveToNext();

                    }
                    c.close();
                    myDatabase.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("TEMP_GOAL----->", String.valueOf(temp_goal));
            Log.i("TEMP_PERCENT---->", String.valueOf(temp_percent));

        }
        else {
            Log.i("TABLE------>", "DOES NOT EXISTS");

            if (gender.equals("male")){
                temp_goal = 3700;
            }
            else if (gender.equals("female")){
                temp_goal = 2700;
            }

        }


        ////////////////////////////////////////////////////////////////////////////////////////////
        //TIPS VISIBILITY

        if (button_name.equals("tips")) {
            heading.setText("Tips");

            t_1.setText("Drink your glass of water slowly with small sips");
            t_2.setText("Hold the water in your mouth for a while before swallowing");
            t_3.setText("Drinking water in sitting posture is better that in a standing or running positon");
            t_4.setText("Do not drink cold water or water with ice");
            t_5.setText("Do not drink water immediately after eating");
            t_6.setText("Do not drink cold water immediately after hot drinks like coffee and tea");
            t_7.setText("Always dink water before urinating and do not drink water immediately after unination");

            t_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t_3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t_4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t_5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t_6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t_7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);



        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //GENERAL VISIBILITY

        if (button_name.equals("general")) {
            heading.setText("General");

            t_7.setVisibility(View.GONE);
            t_6.setVisibility(View.GONE);
            t_5.setVisibility(View.GONE);
            t_4.setVisibility(View.GONE);

            bg_7.setVisibility(View.GONE);
            bg_6.setVisibility(View.GONE);
            bg_5.setVisibility(View.GONE);
            bg_4.setVisibility(View.GONE);

            t_1.setText("Unit");
            t_2.setText("Intake goal");
            t_3.setText("Language");

            rt_1.setVisibility(View.VISIBLE);
            rt_2.setVisibility(View.VISIBLE);
            rt_3.setVisibility(View.VISIBLE);

            rt_1.setText(unit);
            rt_2.setText(temp_goal + " " + "ml");
            rt_3.setText("English");

        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //PERSONAL INFO VISIBILITY

        if (button_name.equals("personal_info")) {
            heading.setText("Personal info");

            t_7.setVisibility(View.GONE);
            t_6.setVisibility(View.GONE);
            t_5.setVisibility(View.GONE);

            bg_7.setVisibility(View.GONE);
            bg_6.setVisibility(View.GONE);
            bg_5.setVisibility(View.GONE);


            t_1.setText("Gender");
            t_2.setText("Weight");
            t_3.setText("Wake up time");
            t_4.setText("Bed time");

            rt_1.setVisibility(View.VISIBLE);
            rt_2.setVisibility(View.VISIBLE);
            rt_3.setVisibility(View.VISIBLE);
            rt_4.setVisibility(View.VISIBLE);

            if (gender.equals("male")){
                rt_1.setText("Male");
            }
            else if (gender.equals("female")){
                rt_1.setText("Female");
            }

            rt_2.setText(weight + " " + unit);

            if (Integer.parseInt(wh) < 10){
                wh = "0" + wh;
            }
            if (Integer.parseInt(wm) < 10){
                wm = "0" + wm;
            }
            rt_3.setText(wh + ":" + wm);

            if (Integer.parseInt(sh) < 10){
                sh = "0" + sh;
            }
            if (Integer.parseInt(sm) < 10){
                sm = "0" + sm;
            }
            rt_4.setText(sh + ":" + sm);

        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //REMAINDER SCHEDULE VISIBILITY

        if (button_name.equals("remainder_schedule")) {
            heading.setText("Remainder schedule");

            t_7.setVisibility(View.GONE);
            t_6.setVisibility(View.GONE);
            t_5.setVisibility(View.GONE);
            t_4.setVisibility(View.GONE);
            t_3.setVisibility(View.GONE);


            bg_7.setVisibility(View.GONE);
            bg_6.setVisibility(View.GONE);
            bg_5.setVisibility(View.GONE);
            bg_4.setVisibility(View.GONE);
            bg_3.setVisibility(View.GONE);


            t_1.setText("Remainder interval");
            t_2.setText("Snooze interval");
/*
            if (Integer.parseInt(remainder_schedule_hrs) > 0) {
                if (Integer.parseInt(remainder_schedule_min) < 10) {
                    t_2.setText("Every " + remainder_schedule_hrs + ":0" + remainder_schedule_min + " hours");
                } else {
                    t_2.setText("Every " + remainder_schedule_hrs + ":" + remainder_schedule_min + " hours");
                }
            } else {
                t_2.setText("Every " + remainder_schedule_min + " min");
            }

/*
            ct_1.setVisibility(View.VISIBLE);
            ct_2.setVisibility(View.VISIBLE);

            if (remainder_schedule.equals("1")){
                ct_1.setChecked(true);
            }
            else if (remainder_schedule.equals("2")){
                ct_2.setChecked(true);
            }

 */

            rt_1.setVisibility(View.VISIBLE);
            rt_2.setVisibility(View.VISIBLE);

            if (Integer.parseInt(remainder_schedule_hrs) < 10){
                temp_remainder_schedule_hrs = "0" + remainder_schedule_hrs;
            }
            else {
                temp_remainder_schedule_hrs = remainder_schedule_hrs;
            }
            if (Integer.parseInt(remainder_schedule_min) < 10){
                temp_remainder_schedule_min = "0" + remainder_schedule_min;
            }
            else {
                temp_remainder_schedule_min = remainder_schedule_min;
            }
            if (Integer.parseInt(remainder_schedule_hrs) > 0 && Integer.parseInt(remainder_schedule_min) > 0) {
                rt_1.setText(temp_remainder_schedule_hrs + ":" + temp_remainder_schedule_min + "hrs");
            }
            if (Integer.parseInt(remainder_schedule_hrs) > 0 && Integer.parseInt(remainder_schedule_min) == 0){
                rt_1.setText(temp_remainder_schedule_hrs + "hrs");
            }
            if (Integer.parseInt(remainder_schedule_hrs) == 0 && Integer.parseInt(remainder_schedule_min) > 0){
                rt_1.setText(temp_remainder_schedule_min + "min");
            }




            if (Integer.parseInt(remainder_snooze_hrs) < 10){
                temp_snooze_hrs = "0" + remainder_snooze_hrs;
            }
            else {
                temp_snooze_hrs = remainder_snooze_hrs;
            }
            if (Integer.parseInt(remainder_snooze_min) < 10){
                temp_snooze_min = "0" + remainder_snooze_min;
            }
            else {
                temp_snooze_min = remainder_snooze_min;
            }
            if (Integer.parseInt(remainder_snooze_hrs) > 0 && Integer.parseInt(remainder_snooze_min) > 0) {
                rt_2.setText(temp_snooze_hrs + ":" + temp_snooze_min + "hrs");
            }
            if (Integer.parseInt(remainder_snooze_hrs) > 0 && Integer.parseInt(remainder_snooze_min) == 0){
                rt_2.setText(temp_snooze_hrs + "hrs");
            }
            if (Integer.parseInt(remainder_snooze_hrs) == 0 && Integer.parseInt(remainder_snooze_min) > 0){
                rt_2.setText(temp_snooze_min + "min");
            }





        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //REMAINDER SOUND VISIBILITY

        if (button_name.equals("remainder_sound")) {
            heading.setText("Remainder sound");

            t_7.setVisibility(View.GONE);

            bg_7.setVisibility(View.GONE);

            t_1.setText("Drops");
            t_2.setText("Splash");
            t_3.setText("Tone");
            t_4.setText("Splash 2");
            t_5.setText("Droplet");
            t_6.setText("Song");

            ct_1.setVisibility(View.VISIBLE);
            ct_2.setVisibility(View.VISIBLE);
            ct_3.setVisibility(View.VISIBLE);
            ct_4.setVisibility(View.VISIBLE);
            ct_5.setVisibility(View.VISIBLE);
            ct_6.setVisibility(View.VISIBLE);

            mplayer = MediaPlayer.create(sub_menu.this, R.raw.water_1);

            if (sound.equals("1")){
                ct_1.setChecked(true);
            }
            else if (sound.equals("2")){
                ct_2.setChecked(true);
            }
            else if (sound.equals("3")){
                ct_3.setChecked(true);
            }
            else if (sound.equals("4")){
                ct_4.setChecked(true);
            }
            else if (sound.equals("5")){
                ct_5.setChecked(true);
            }
            else if (sound.equals("6")){
                ct_6.setChecked(true);
            }

        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //REMAINDER MODE VISIBILITY

        if (button_name.equals("remainder_mode")) {
            heading.setText("Remainder mode");

            t_7.setVisibility(View.GONE);
            t_6.setVisibility(View.GONE);


            bg_7.setVisibility(View.GONE);
            bg_6.setVisibility(View.GONE);

            t_1.setText("Sound and vibrate");
            t_2.setText("Sound only");
            t_3.setText("Vibrate only");
            t_4.setText("Notification only");
            t_5.setText("Turn off");

            ct_1.setVisibility(View.VISIBLE);
            ct_2.setVisibility(View.VISIBLE);
            ct_3.setVisibility(View.VISIBLE);
            ct_4.setVisibility(View.VISIBLE);
            ct_5.setVisibility(View.VISIBLE);

            if (mode.equals("1")){
                ct_1.setChecked(true);
            }
            else if (mode.equals("2")){
                ct_2.setChecked(true);
            }
            else if (mode.equals("3")){
                ct_3.setChecked(true);
            }
            else if (mode.equals("4")){
                ct_4.setChecked(true);
            }
            else if (mode.equals("5")){
                ct_5.setChecked(true);
            }

        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //FURTHER REMAINDER VISIBILITY

        if (button_name.equals("further_remainder")) {
            heading.setText("Further remainder");

            t_7.setVisibility(View.GONE);
            t_6.setVisibility(View.GONE);
            t_5.setVisibility(View.GONE);
            t_4.setVisibility(View.GONE);
            t_3.setVisibility(View.GONE);
            t_2.setVisibility(View.GONE);


            bg_7.setVisibility(View.GONE);
            bg_6.setVisibility(View.GONE);
            bg_5.setVisibility(View.GONE);
            bg_4.setVisibility(View.GONE);
            bg_3.setVisibility(View.GONE);
            bg_2.setVisibility(View.GONE);

            //t_1.setText("Still reminds you when the goal is achieved");
            t_1.setText("Further remind after goal is achieved");
            t_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            ct_1.setVisibility(View.VISIBLE);

            if (further.equals("1")){
                ct_1.setChecked(true);
            }
            else if (further.equals("0")){
                ct_1.setChecked(false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////


        ct_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   if (button_name.equals("further_remainder")) {
                       if (isChecked) {
                           sharedPreferences.edit().putString("further", "1").apply();
                           further = sharedPreferences.getString("further", "");
                       }
                       if (!isChecked) {
                           sharedPreferences.edit().putString("further", "0").apply();
                           further = sharedPreferences.getString("further", "");
                       }
                   }
                   if (button_name.equals("remainder_mode")) {
                       if (isChecked) {
                           sharedPreferences.edit().putString("mode", "1").apply();
                           ct_2.setChecked(false);
                           ct_3.setChecked(false);
                           ct_4.setChecked(false);
                           ct_5.setChecked(false);
                       }
                   }
                   if (button_name.equals("remainder_sound")) {
                       if (isChecked) {
                           sharedPreferences.edit().putString("sound", "1").apply();
                           ct_2.setChecked(false);
                           ct_3.setChecked(false);
                           ct_4.setChecked(false);
                           ct_5.setChecked(false);
                           ct_6.setChecked(false);

                           mplayer.stop();
                           mplayer = MediaPlayer.create(sub_menu.this, R.raw.water_1);
                           audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
                           mplayer.start();
                       }
                       else {
                           mplayer.stop();
                       }
                   }
                   if (button_name.equals("remainder_schedule")) {
                       if (isChecked) {
                           sharedPreferences.edit().putString("remainder_schedule", "1").apply();
                           ct_2.setChecked(false);
                       }
                   }
               }
           }
        );

        ct_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if (button_name.equals("remainder_mode")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("mode", "2").apply();
                            ct_1.setChecked(false);
                            ct_3.setChecked(false);
                            ct_4.setChecked(false);
                            ct_5.setChecked(false);
                        }
                    }
                    if (button_name.equals("remainder_sound")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("sound", "2").apply();
                            ct_1.setChecked(false);
                            ct_3.setChecked(false);
                            ct_4.setChecked(false);
                            ct_5.setChecked(false);
                            ct_6.setChecked(false);

                            mplayer.stop();
                            mplayer = MediaPlayer.create(sub_menu.this, R.raw.water_2);
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
                            mplayer.start();
                        }
                        else {
                            mplayer.stop();
                        }
                    }
                    if (button_name.equals("remainder_schedule")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("remainder_schedule", "2").apply();
                            ct_1.setChecked(false);
/*
                            hour_picker.setMinValue(0);
                            hour_picker.setMaxValue(12);
                            hour_picker.setValue(Integer.parseInt(remainder_schedule_hrs));
                            minute_picker.setMinValue(0);
                            minute_picker.setMaxValue(59);
                            minute_picker.setValue(Integer.parseInt(remainder_schedule_min));
                            String[] sec = new String[60];
                            String[] hrs = new String[13];
                            for (int i = 0; i < 60; i++){
                                sec[i] = i + " min";
                                if (i < 13){
                                    hrs[i] = i + " hour";
                                }
                            }
                            hour_picker.setDisplayedValues(hrs);
                            minute_picker.setDisplayedValues(sec);
                            hour_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                            minute_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                            alert_layout.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
                            params.gravity = Gravity.CENTER;
                            LinearLayout.LayoutParams numPicerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            numPicerParams.weight = 1;
                            LinearLayout.LayoutParams qPicerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            qPicerParams.weight = 1;
                            alert_layout.setLayoutParams(params);
                            alert_layout.addView(hour_picker,numPicerParams);
                            alert_layout.addView(minute_picker,qPicerParams);

                            AlertDialog.Builder builder = new AlertDialog.Builder(sub_menu.this);
                            builder.setView(alert_layout);
                            builder.setTitle("Choose time");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (hour_picker.getValue() == 0 && minute_picker.getValue() == 0) {
                                        Toast.makeText(getApplicationContext(), "Please choose an appropriate time", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        if (hour_picker.getValue() > 0) {
                                            if (minute_picker.getValue() < 10) {
                                                t_2.setText("Every " + hour_picker.getValue() + ":0" + minute_picker.getValue() + " hours");
                                                sharedPreferences.edit().putString("remainder_schedule_hrs", String.valueOf(hour_picker.getValue())).apply();
                                                sharedPreferences.edit().putString("remainder_schedule_min", String.valueOf(minute_picker.getValue())).apply();
                                            } else {
                                                t_2.setText("Every " + hour_picker.getValue() + ":" + minute_picker.getValue() + " hours");
                                                sharedPreferences.edit().putString("remainder_schedule_hrs", String.valueOf(hour_picker.getValue())).apply();
                                                sharedPreferences.edit().putString("remainder_schedule_min", String.valueOf(minute_picker.getValue())).apply();
                                            }
                                        } else {
                                            t_2.setText("Every " + minute_picker.getValue() + " min");
                                            sharedPreferences.edit().putString("remainder_schedule_hrs", String.valueOf(hour_picker.getValue())).apply();
                                            sharedPreferences.edit().putString("remainder_schedule_min", String.valueOf(minute_picker.getValue())).apply();
                                        }
                                    }
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
*/
                        }
                    }
                }
            }
        );

        ct_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                    if (button_name.equals("remainder_mode")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("mode", "3").apply();
                            ct_1.setChecked(false);
                            ct_2.setChecked(false);
                            ct_4.setChecked(false);
                            ct_5.setChecked(false);
                        }
                    }
                    if (button_name.equals("remainder_sound")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("sound", "3").apply();
                            ct_1.setChecked(false);
                            ct_2.setChecked(false);
                            ct_4.setChecked(false);
                            ct_5.setChecked(false);
                            ct_6.setChecked(false);

                            mplayer.stop();
                            mplayer = MediaPlayer.create(sub_menu.this, R.raw.water_3);
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
                            mplayer.start();
                        }
                        else {
                            mplayer.stop();
                        }
                    }
                }
            }
        );

        ct_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if (button_name.equals("remainder_mode")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("mode", "4").apply();
                            ct_1.setChecked(false);
                            ct_2.setChecked(false);
                            ct_3.setChecked(false);
                            ct_5.setChecked(false);
                        }
                    }
                    if (button_name.equals("remainder_sound")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("sound", "4").apply();
                            ct_1.setChecked(false);
                            ct_2.setChecked(false);
                            ct_3.setChecked(false);
                            ct_5.setChecked(false);
                            ct_6.setChecked(false);

                            mplayer.stop();
                            mplayer = MediaPlayer.create(sub_menu.this, R.raw.water_4);
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
                            mplayer.start();
                        }
                        else {
                            mplayer.stop();
                        }
                    }
                }
            }
        );

        ct_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if (button_name.equals("remainder_mode")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("mode", "5").apply();
                            ct_1.setChecked(false);
                            ct_2.setChecked(false);
                            ct_3.setChecked(false);
                            ct_4.setChecked(false);
                        }
                    }
                    if (button_name.equals("remainder_sound")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("sound", "5").apply();
                            ct_1.setChecked(false);
                            ct_2.setChecked(false);
                            ct_3.setChecked(false);
                            ct_4.setChecked(false);
                            ct_6.setChecked(false);

                            mplayer.stop();
                            mplayer = MediaPlayer.create(sub_menu.this, R.raw.water_5);
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
                            mplayer.start();
                        }
                        else {
                            mplayer.stop();
                        }
                    }
                }
            }
        );

        ct_6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if (button_name.equals("remainder_sound")) {
                        if (isChecked) {
                            sharedPreferences.edit().putString("sound", "6").apply();
                            ct_1.setChecked(false);
                            ct_2.setChecked(false);
                            ct_3.setChecked(false);
                            ct_4.setChecked(false);
                            ct_5.setChecked(false);

                            mplayer.stop();
                            mplayer = MediaPlayer.create(sub_menu.this, R.raw.water_6);
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
                            mplayer.start();
                        }
                        else {
                            mplayer.stop();
                        }
                    }
                }
            }
        );








        ////////////////////////////////////////////////////////////////////////////////////////////
        //END OF ONCREATE
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    //INSERT DATA FUNCTION


    public void insert_data(int hours, int day, int date, int month, int water, int goal, int percent){
        Log.i("INSIDE INSERT----->", "HI IAM INSIDE IT");
        try {
            myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS userdata (hours INT(3), day INT(2), date INT(3), month INT(3), water INT(5), goal INT(5), percent INT(3), id INTEGER PRIMARY KEY)");

            myDatabase.execSQL("INSERT INTO userdata (hours, day, date, month, water, goal, percent) VALUES (" + hours + "," + day + "," + date + "," + month + "," + water + "," + goal + "," + percent + ")");
            myDatabase.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //BACK BUTTON

    public void back_button(View view){

        Intent intent = new Intent(sub_menu.this, menu.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    //CHECK IF TABLE EXISTS

    public boolean checkForTableExists(){
        myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
        Cursor c = myDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='userdata'", null);
        if (c.getCount() > 0) {
            myDatabase.close();
            c.close();
            return true;
        }
        myDatabase.close();
        c.close();
        return false;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ONCLICK BACKGROUND

    public void bg_1(View view) {

        if (button_name.equals("general")) {
            final String[] unit_selected = {"Kg", "lbs"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose unit");
            builder.setItems(unit_selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedPreferences.edit().putString("unit", unit_selected[which]).apply();
                    rt_1.setText(sharedPreferences.getString("unit", ""));
                    if(which == 0){
                        if (unit.equals("lbs")) {
                            sharedPreferences.edit().putString("weight", String.valueOf((int) Math.round(Float.parseFloat(weight) / 2.2))).apply();
                            sharedPreferences.edit().putString("unit", "kg").apply();
                            weight = sharedPreferences.getString("weight", "");
                        }
                    }
                    else if(which == 1){
                        if (unit.equals("kg")) {
                            sharedPreferences.edit().putString("weight", String.valueOf((int) Math.round(Float.parseFloat(weight) * 2.2))).apply();
                            sharedPreferences.edit().putString("unit", "lbs").apply();
                            weight = sharedPreferences.getString("weight", "");
                        }
                    }
                }
            });
            builder.show();
        }

        if (button_name.equals("personal_info")){
            final String[] gender_selected = {"Male", "Female"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select your gender");
            builder.setItems(gender_selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0){
                        sharedPreferences.edit().putString("gender", "male").apply();
                        rt_1.setText("Male");
                        gender = sharedPreferences.getString("gender", "");
                    }
                    else if(which == 1){
                        sharedPreferences.edit().putString("gender", "female").apply();
                        rt_1.setText("Female");
                        gender = sharedPreferences.getString("gender", "");
                    }
                }
            });
            builder.show();
        }


        if (button_name.equals("remainder_schedule")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    if (((hourOfDay * 60) + minute) >= 30) {
                        sharedPreferences.edit().putString("remainder_schedule_hrs", String.valueOf(hourOfDay)).apply();
                        sharedPreferences.edit().putString("remainder_schedule_min", String.valueOf(minute)).apply();

                        temp_remainder_schedule_hrs = null;
                        temp_remainder_schedule_min = null;

                        if (hourOfDay < 10) {
                            temp_remainder_schedule_hrs = "0" + hourOfDay;
                        } else {
                            temp_remainder_schedule_hrs = "" + hourOfDay;
                        }
                        if (minute < 10) {
                            temp_remainder_schedule_min = "0" + minute;
                        } else {
                            temp_remainder_schedule_min = "" + minute;
                        }
                        if (hourOfDay > 0 && minute > 0) {
                            rt_1.setText(temp_remainder_schedule_hrs + ":" + temp_remainder_schedule_min + "hrs");
                        }
                        if (hourOfDay > 0 && minute == 0){
                            rt_1.setText(temp_remainder_schedule_hrs + "hrs");
                        }
                        if (hourOfDay == 0 && minute > 0){
                            rt_1.setText(temp_remainder_schedule_min + "min");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Remainder interval should be greater than 30min", Toast.LENGTH_SHORT).show();
                    }
                }
            }, Integer.parseInt(sharedPreferences.getString("remainder_schedule_hrs", "0")), Integer.parseInt(sharedPreferences.getString("remainder_schedule_min", "0")), true);
            timePickerDialog.show();
        }


    }
    public void bg_2(View view){

        if (button_name.equals("general")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(sub_menu.this);
            builder.setTitle("Enter water intake goal in ml");
            final EditText input = new EditText(sub_menu.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (Integer.parseInt(String.valueOf(input.getText())) > 100) {
                        insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), 0, Integer.parseInt(String.valueOf(input.getText())), (int) temp_percent);
                        rt_2.setText(input.getText() + " ml");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter water goal more than 100ml", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }

        if (button_name.equals("personal_info")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(sub_menu.this);
            builder.setTitle("Enter your weight in " + unit);
            final EditText input = new EditText(sub_menu.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Integer.parseInt(String.valueOf(input.getText())) > 10) {
                        sharedPreferences.edit().putString("weight", String.valueOf(input.getText())).apply();
                        rt_2.setText(input.getText() + " " + unit);
                        weight = sharedPreferences.getString("weight", "");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter weight more than 10kg", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }


        if (button_name.equals("remainder_schedule")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    if (((hourOfDay * 60) + minute) < ((Integer.parseInt(sharedPreferences.getString("remainder_schedule_hrs", "0")) * 60) + Integer.parseInt(sharedPreferences.getString("remainder_schedule_min", "45")))) {
                        sharedPreferences.edit().putString("remainder_snooze_hrs", String.valueOf(hourOfDay)).apply();
                        sharedPreferences.edit().putString("remainder_snooze_min", String.valueOf(minute)).apply();

                        temp_snooze_hrs = null;
                        temp_snooze_min = null;

                        if (hourOfDay < 10) {
                            temp_snooze_hrs = "0" + hourOfDay;
                        } else {
                            temp_snooze_hrs = "" + hourOfDay;
                        }
                        if (minute < 10) {
                            temp_snooze_min = "0" + minute;
                        } else {
                            temp_snooze_min = "" + minute;
                        }
                        if (hourOfDay > 0 && minute > 0) {
                            rt_2.setText(temp_snooze_hrs + ":" + temp_snooze_min + "hrs");
                        }
                        if (hourOfDay > 0 && minute == 0){
                            rt_2.setText(temp_snooze_hrs + "hrs");
                        }
                        if (hourOfDay == 0 && minute > 0){
                            rt_2.setText(temp_snooze_min + "min");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Snooze interval should not be greater than remainder interval", Toast.LENGTH_SHORT).show();
                    }
                }
            }, Integer.parseInt(sharedPreferences.getString("remainder_snooze_hrs", "0")), Integer.parseInt(sharedPreferences.getString("remainder_snooze_min", "0")), true);
            timePickerDialog.show();
        }



    }
    public void bg_3(View view){

        if (button_name.equals("personal_info")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay < 10){
                            wh = "0" + hourOfDay;
                        }
                        if (minute < 10){
                            wm = "0" + minute;
                        }
                        sharedPreferences.edit().putString("wh", String.valueOf(hourOfDay)).apply();
                        sharedPreferences.edit().putString("wm", String.valueOf(minute)).apply();
                        rt_3.setText(wh + ":" + wm);
                        wh = sharedPreferences.getString("wh", "");
                        wm = sharedPreferences.getString("wm", "");
                    }
                }, Integer.parseInt(wh), Integer.parseInt(wm), false);
            timePickerDialog.show();
        }


    }
    public void bg_4(View view){

        if (button_name.equals("personal_info")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    sharedPreferences.edit().putString("sh", String.valueOf(hourOfDay)).apply();
                    sharedPreferences.edit().putString("sm", String.valueOf(minute)).apply();
                    sh = sharedPreferences.getString("sh", "");
                    sm = sharedPreferences.getString("sm", "");
                    if (hourOfDay < 10){
                        sh = "0" + hourOfDay;
                    }
                    if (minute < 10){
                        sm = "0" + minute;
                    }
                    rt_4.setText(sh + ":" + sm);
                    sh = sharedPreferences.getString("sh", "");
                    sm = sharedPreferences.getString("sm", "");
                }
            }, Integer.parseInt(sh), Integer.parseInt(sm), false);
            timePickerDialog.show();
        }



    }
    public void bg_5(View view){

    }
    public void bg_6(View view){

    }
    public void bg_7(View view){

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ONCLICK TEXT

    public void t_1(View view){

        if (button_name.equals("general")) {
            final String[] unit_selected = {"Kg", "lbs"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose unit");
            builder.setItems(unit_selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedPreferences.edit().putString("unit", unit_selected[which]).apply();
                    rt_1.setText(sharedPreferences.getString("unit", ""));
                    if(which == 0){
                        if (unit.equals("lbs")) {
                            sharedPreferences.edit().putString("weight", String.valueOf((int) Math.round(Float.parseFloat(weight) / 2.2))).apply();
                            sharedPreferences.edit().putString("unit", "kg").apply();
                            weight = sharedPreferences.getString("weight", "");
                        }
                    }
                    else if(which == 1){
                        if (unit.equals("kg")) {
                            sharedPreferences.edit().putString("weight", String.valueOf((int) Math.round(Float.parseFloat(weight) * 2.2))).apply();
                            sharedPreferences.edit().putString("unit", "lbs").apply();
                            weight = sharedPreferences.getString("weight", "");
                        }
                    }
                }
            });
            builder.show();
        }

        if (button_name.equals("personal_info")){
            final String[] gender_selected = {"Male", "Female"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select your gender");
            builder.setItems(gender_selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0){
                        sharedPreferences.edit().putString("gender", "male").apply();
                        rt_1.setText("Male");
                        gender = sharedPreferences.getString("gender", "");
                    }
                    else if(which == 1){
                        sharedPreferences.edit().putString("gender", "female").apply();
                        rt_1.setText("Female");
                        gender = sharedPreferences.getString("gender", "");
                    }
                }
            });
            builder.show();
        }


        if (button_name.equals("remainder_schedule")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    if (((hourOfDay * 60) + minute) >= 30) {
                        sharedPreferences.edit().putString("remainder_schedule_hrs", String.valueOf(hourOfDay)).apply();
                        sharedPreferences.edit().putString("remainder_schedule_min", String.valueOf(minute)).apply();

                        temp_remainder_schedule_hrs = null;
                        temp_remainder_schedule_min = null;

                        if (hourOfDay < 10) {
                            temp_remainder_schedule_hrs = "0" + hourOfDay;
                        } else {
                            temp_remainder_schedule_hrs = "" + hourOfDay;
                        }
                        if (minute < 10) {
                            temp_remainder_schedule_min = "0" + minute;
                        } else {
                            temp_remainder_schedule_min = "" + minute;
                        }
                        if (hourOfDay > 0 && minute > 0) {
                            rt_1.setText(temp_remainder_schedule_hrs + ":" + temp_remainder_schedule_min + "hrs");
                        }
                        if (hourOfDay > 0 && minute == 0){
                            rt_1.setText(temp_remainder_schedule_hrs + "hrs");
                        }
                        if (hourOfDay == 0 && minute > 0){
                            rt_1.setText(temp_remainder_schedule_min + "min");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Remainder interval should be greater than 30min", Toast.LENGTH_SHORT).show();
                    }
                }
            }, Integer.parseInt(sharedPreferences.getString("remainder_schedule_hrs", "0")), Integer.parseInt(sharedPreferences.getString("remainder_schedule_min", "0")), true);
            timePickerDialog.show();
        }

    }
    public void t_2(View view){

        if (button_name.equals("general")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(sub_menu.this);
            builder.setTitle("Enter water intake goal in ml");
            final EditText input = new EditText(sub_menu.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (Integer.parseInt(String.valueOf(input.getText())) > 100) {
                        insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), 0, Integer.parseInt(String.valueOf(input.getText())), (int) temp_percent);
                        rt_2.setText(input.getText() + " ml");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter water goal more than 100ml", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }

        if (button_name.equals("personal_info")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(sub_menu.this);
            builder.setTitle("Enter your weight in " + unit);
            final EditText input = new EditText(sub_menu.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Integer.parseInt(String.valueOf(input.getText())) > 10) {
                        sharedPreferences.edit().putString("weight", String.valueOf(input.getText())).apply();
                        rt_2.setText(input.getText() + " " + unit);
                        weight = sharedPreferences.getString("weight", "");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter weight more than 10kg", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }



        if (button_name.equals("remainder_schedule")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    if (((hourOfDay * 60) + minute) < ((Integer.parseInt(sharedPreferences.getString("remainder_schedule_hrs", "0")) * 60) + Integer.parseInt(sharedPreferences.getString("remainder_schedule_min", "45")))) {
                        sharedPreferences.edit().putString("remainder_snooze_hrs", String.valueOf(hourOfDay)).apply();
                        sharedPreferences.edit().putString("remainder_snooze_min", String.valueOf(minute)).apply();

                        temp_snooze_hrs = null;
                        temp_snooze_min = null;

                        if (hourOfDay < 10) {
                            temp_snooze_hrs = "0" + hourOfDay;
                        } else {
                            temp_snooze_hrs = "" + hourOfDay;
                        }
                        if (minute < 10) {
                            temp_snooze_min = "0" + minute;
                        } else {
                            temp_snooze_min = "" + minute;
                        }
                        if (hourOfDay > 0 && minute > 0) {
                            rt_2.setText(temp_snooze_hrs + ":" + temp_snooze_min + "hrs");
                        }
                        if (hourOfDay > 0 && minute == 0){
                            rt_2.setText(temp_snooze_hrs + "hrs");
                        }
                        if (hourOfDay == 0 && minute > 0){
                            rt_2.setText(temp_snooze_min + "min");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Snooze interval should not be greater than remainder interval", Toast.LENGTH_SHORT).show();
                    }
                }
            }, Integer.parseInt(sharedPreferences.getString("remainder_snooze_hrs", "0")), Integer.parseInt(sharedPreferences.getString("remainder_snooze_min", "0")), true);
            timePickerDialog.show();
        }



    }
    public void t_3(View view){

        if (button_name.equals("personal_info")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (hourOfDay < 10){
                        wh = "0" + hourOfDay;
                    }
                    if (minute < 10){
                        wm = "0" + minute;
                    }
                    sharedPreferences.edit().putString("wh", String.valueOf(hourOfDay)).apply();
                    sharedPreferences.edit().putString("wm", String.valueOf(minute)).apply();
                    rt_3.setText(wh + ":" + wm);
                    wh = sharedPreferences.getString("wh", "");
                    wm = sharedPreferences.getString("wm", "");
                }
            }, Integer.parseInt(wh), Integer.parseInt(wm), false);
            timePickerDialog.show();
        }


    }
    public void t_4(View view){

        if (button_name.equals("personal_info")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    sharedPreferences.edit().putString("sh", String.valueOf(hourOfDay)).apply();
                    sharedPreferences.edit().putString("sm", String.valueOf(minute)).apply();
                    sh = sharedPreferences.getString("sh", "");
                    sm = sharedPreferences.getString("sm", "");
                    if (hourOfDay < 10){
                        sh = "0" + hourOfDay;
                    }
                    if (minute < 10){
                        sm = "0" + minute;
                    }
                    rt_4.setText(sh + ":" + sm);
                    sh = sharedPreferences.getString("sh", "");
                    sm = sharedPreferences.getString("sm", "");
                }
            }, Integer.parseInt(sh), Integer.parseInt(sm), false);
            timePickerDialog.show();
        }


    }
    public void t_5(View view){

    }
    public void t_6(View view){

    }
    public void t_7(View view){

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ONCLICK SIDE TEXT

    public void rt_1(View view){

        if (button_name.equals("general")) {
            final String[] unit_selected = {"Kg", "lbs"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose unit");
            builder.setItems(unit_selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedPreferences.edit().putString("unit", unit_selected[which]).apply();
                    rt_1.setText(sharedPreferences.getString("unit", ""));
                    if(which == 0){
                        if (unit.equals("lbs")) {
                            sharedPreferences.edit().putString("weight", String.valueOf((int) Math.round(Float.parseFloat(weight) / 2.2))).apply();
                            sharedPreferences.edit().putString("unit", "kg").apply();
                            weight = sharedPreferences.getString("weight", "");
                        }
                    }
                    else if(which == 1){
                        if (unit.equals("kg")) {
                            sharedPreferences.edit().putString("weight", String.valueOf((int) Math.round(Float.parseFloat(weight) * 2.2))).apply();
                            sharedPreferences.edit().putString("unit", "lbs").apply();
                            weight = sharedPreferences.getString("weight", "");
                        }
                    }
                }
            });
            builder.show();
        }

        if (button_name.equals("personal_info")){
            final String[] gender_selected = {"Male", "Female"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select your gender");
            builder.setItems(gender_selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0){
                        sharedPreferences.edit().putString("gender", "male").apply();
                        rt_1.setText("Male");
                        gender = sharedPreferences.getString("gender", "");
                    }
                    else if(which == 1){
                        sharedPreferences.edit().putString("gender", "female").apply();
                        rt_1.setText("Female");
                        gender = sharedPreferences.getString("gender", "");
                    }
                }
            });
            builder.show();
        }

/*
        if (button_name.equals("further_remainder")) {
            final String[] yes_no = {"Yes", "No"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select your gender");
            builder.setItems(yes_no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        sharedPreferences.edit().putString("further", "yes").apply();
                        rt_1.setText("Yes");
                        further = sharedPreferences.getString("further", "");
                    } else if (which == 1) {
                        sharedPreferences.edit().putString("further", "no").apply();
                        rt_1.setText("No");
                        further = sharedPreferences.getString("further", "");
                    }
                }
            });
            builder.show();
        }

 */


        if (button_name.equals("remainder_schedule")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    if (((hourOfDay * 60) + minute) >= 30) {
                        sharedPreferences.edit().putString("remainder_schedule_hrs", String.valueOf(hourOfDay)).apply();
                        sharedPreferences.edit().putString("remainder_schedule_min", String.valueOf(minute)).apply();

                        temp_remainder_schedule_hrs = null;
                        temp_remainder_schedule_min = null;

                        if (hourOfDay < 10) {
                            temp_remainder_schedule_hrs = "0" + hourOfDay;
                        } else {
                            temp_remainder_schedule_hrs = "" + hourOfDay;
                        }
                        if (minute < 10) {
                            temp_remainder_schedule_min = "0" + minute;
                        } else {
                            temp_remainder_schedule_min = "" + minute;
                        }
                        if (hourOfDay > 0 && minute > 0) {
                            rt_1.setText(temp_remainder_schedule_hrs + ":" + temp_remainder_schedule_min + "hrs");
                        }
                        if (hourOfDay > 0 && minute == 0){
                            rt_1.setText(temp_remainder_schedule_hrs + "hrs");
                        }
                        if (hourOfDay == 0 && minute > 0){
                            rt_1.setText(temp_remainder_schedule_min + "min");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Remainder interval should be greater than 30min", Toast.LENGTH_SHORT).show();
                    }
                }
            }, Integer.parseInt(sharedPreferences.getString("remainder_schedule_hrs", "0")), Integer.parseInt(sharedPreferences.getString("remainder_schedule_min", "0")), true);
            timePickerDialog.show();
        }

    }
    public void rt_2(View view){

        if (button_name.equals("general")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(sub_menu.this);
            builder.setTitle("Enter water intake goal in ml");
            final EditText input = new EditText(sub_menu.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (Integer.parseInt(String.valueOf(input.getText())) > 100) {
                        insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), 0, Integer.parseInt(String.valueOf(input.getText())), (int) temp_percent);
                        rt_2.setText(input.getText() + " ml");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter water goal more than 100ml", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }

        if (button_name.equals("personal_info")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(sub_menu.this);
            builder.setTitle("Enter your weight in " + unit);
            final EditText input = new EditText(sub_menu.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Integer.parseInt(String.valueOf(input.getText())) > 10) {
                        sharedPreferences.edit().putString("weight", String.valueOf(input.getText())).apply();
                        rt_2.setText(input.getText() + " " + unit);
                        weight = sharedPreferences.getString("weight", "");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter weight more than 10kg", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }


        if (button_name.equals("remainder_schedule")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    if (((hourOfDay * 60) + minute) < ((Integer.parseInt(sharedPreferences.getString("remainder_schedule_hrs", "0")) * 60) + Integer.parseInt(sharedPreferences.getString("remainder_schedule_min", "45")))) {
                        sharedPreferences.edit().putString("remainder_snooze_hrs", String.valueOf(hourOfDay)).apply();
                        sharedPreferences.edit().putString("remainder_snooze_min", String.valueOf(minute)).apply();

                        temp_snooze_hrs = null;
                        temp_snooze_min = null;

                        if (hourOfDay < 10) {
                            temp_snooze_hrs = "0" + hourOfDay;
                        } else {
                            temp_snooze_hrs = "" + hourOfDay;
                        }
                        if (minute < 10) {
                            temp_snooze_min = "0" + minute;
                        } else {
                            temp_snooze_min = "" + minute;
                        }
                        if (hourOfDay > 0 && minute > 0) {
                            rt_2.setText(temp_snooze_hrs + ":" + temp_snooze_min + "hrs");
                        }
                        if (hourOfDay > 0 && minute == 0){
                            rt_2.setText(temp_snooze_hrs + "hrs");
                        }
                        if (hourOfDay == 0 && minute > 0){
                            rt_2.setText(temp_snooze_min + "min");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Snooze interval should not be greater than remainder interval", Toast.LENGTH_SHORT).show();
                    }
                }
            }, Integer.parseInt(sharedPreferences.getString("remainder_snooze_hrs", "0")), Integer.parseInt(sharedPreferences.getString("remainder_snooze_min", "0")), true);
            timePickerDialog.show();
        }


    }
    public void rt_3(View view){

        if (button_name.equals("personal_info")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (hourOfDay < 10){
                        wh = "0" + hourOfDay;
                    }
                    if (minute < 10){
                        wm = "0" + minute;
                    }
                    sharedPreferences.edit().putString("wh", String.valueOf(hourOfDay)).apply();
                    sharedPreferences.edit().putString("wm", String.valueOf(minute)).apply();
                    rt_3.setText(wh + ":" + wm);
                    wh = sharedPreferences.getString("wh", "");
                    wm = sharedPreferences.getString("wm", "");
                }
            }, Integer.parseInt(wh), Integer.parseInt(wm), false);
            timePickerDialog.show();
        }


    }
    public void rt_4(View view){

        if (button_name.equals("personal_info")) {
            timePickerDialog = new TimePickerDialog(sub_menu.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    sharedPreferences.edit().putString("sh", String.valueOf(hourOfDay)).apply();
                    sharedPreferences.edit().putString("sm", String.valueOf(minute)).apply();
                    sh = sharedPreferences.getString("sh", "");
                    sm = sharedPreferences.getString("sm", "");
                    if (hourOfDay < 10){
                        sh = "0" + hourOfDay;
                    }
                    if (minute < 10){
                        sm = "0" + minute;
                    }
                    rt_4.setText(sh + ":" + sm);
                    sh = sharedPreferences.getString("sh", "");
                    sm = sharedPreferences.getString("sm", "");
                }
            }, Integer.parseInt(sh), Integer.parseInt(sm), false);
            timePickerDialog.show();
        }


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////





    ////////////////////////////////////////////////////////////////////////////////////////////////
    //END OF ACTIVITY
}
