package com.example.version1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Notification;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import me.itangqi.waveloadingview.WaveLoadingView;

public class main extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ImageView gender_profile;
    Date currentTime;
    TextView goodMorning;
    TextView percentage;
    ProgressBar progressBar;
    WaveLoadingView waveLoadingView;
    Button recent;
    TextView your_goal_ml;
    Button custom;
    TextView percent_sign;
    TextView your_goal;
    TextView nextRemainder;
    TextView hydrated;
    SQLiteDatabase myDatabase;

    ImageView w100ml;
    ImageView cup;
    TextView t100;
    TextView t100ml;

    ImageView w150ml;
    ImageView mug;
    TextView t150;
    TextView t150ml;

    ImageView w200ml;
    ImageView glass;
    TextView t200;
    TextView t200ml;

    ImageView w300ml;
    ImageView big_mug;
    TextView t300;
    TextView t300ml;

    ImageView w400ml;
    ImageView bottle;
    TextView t400;
    TextView t400ml;

    ImageView wc1;
    ImageView custom_ml_1;
    TextView tc1;
    TextView tc1ml;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLE DECLARE

    String year;
    String month;
    String date;
    String hours;
    String minutes;

    float water_selected;
    float last_updated = 100;                    //DEFAULT
    float current_hydrate_status = 0;
    float goal_water;
    boolean custom_selected;
    int user_custom_ml;
    int day;
    float hydration_percentage;
    float temp_percent;
    float temp_goal;
    float temp_date;
    int temp_water;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //SHAREDPREFERENCE VARIABLE DECLARE

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
    String channel_id;
    String water_log_min;
    String userDataAcquired;




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ON_BACK_PRESSED


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ONCREATE


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.main);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //SCREEN SETTING

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //INITIALIZE

        sharedPreferences = this.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);
        currentTime = Calendar.getInstance().getTime();
        gender_profile = findViewById(R.id.gender_profile);
        goodMorning = findViewById(R.id.goodMorning);
        percentage = findViewById(R.id.percentage);
        progressBar = findViewById(R.id.progressBar);
        waveLoadingView = findViewById(R.id.waveLoadingView);
        recent = findViewById(R.id.recent);
        your_goal_ml = findViewById(R.id.your_goal_ml);
        custom = findViewById(R.id.custom);
        percent_sign = findViewById(R.id.percent_sign);
        your_goal = findViewById(R.id.your_goal);
        nextRemainder = findViewById(R.id.nextRemainder);
        hydrated = findViewById(R.id.hydrated);

        w100ml = findViewById(R.id.w100ml);
        cup = findViewById(R.id.cup);
        t100 = findViewById(R.id.t100);
        t100ml = findViewById(R.id.t100ml);

        w150ml = findViewById(R.id.w150ml);
        mug = findViewById(R.id.mug);
        t150 = findViewById(R.id.t150);
        t150ml = findViewById(R.id.t150ml);

        w200ml = findViewById(R.id.w200ml);
        glass = findViewById(R.id.glass);
        t200 = findViewById(R.id.t200);
        t200ml = findViewById(R.id.t200ml);

        w300ml = findViewById(R.id.w300ml);
        big_mug = findViewById(R.id.big_mug);
        t300 = findViewById(R.id.t300);
        t300ml = findViewById(R.id.t300ml);

        w400ml = findViewById(R.id.w400ml);
        bottle = findViewById(R.id.bottle);
        t400 = findViewById(R.id.t400);
        t400ml = findViewById(R.id.t400ml);

        wc1 = findViewById(R.id.wc1);
        custom_ml_1 = findViewById(R.id.custom_ml_1);
        tc1 = findViewById(R.id.tc1);
        tc1ml = findViewById(R.id.tc1ml);





        ////////////////////////////////////////////////////////////////////////////////////////////
        //VARIABLE INITIALIZE

        year = new SimpleDateFormat("yyyy").format(currentTime);
        month = new SimpleDateFormat("MM").format(currentTime);
        date = new SimpleDateFormat("dd").format(currentTime);
        hours = new SimpleDateFormat("HH").format(currentTime);
        minutes = new SimpleDateFormat("mm").format(currentTime);

        day = currentTime.getDay();


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
        channel_id = sharedPreferences.getString("channel_id", "");
        water_log_min = sharedPreferences.getString("water_log_min", "");
        userDataAcquired = sharedPreferences.getString("userDataAcquired", "");

        sharedPreferences.edit().putString("main_is_active", "yes").apply();


        ////////////////////////////////////////////////////////////////////////////////////////////


        if(Notification.calculateNextTime(getApplicationContext()) != 0) {
            callNotification();
        }

        //Notification.deleteNotificationChannel(getApplicationContext(), "my_channel_1");


        //Notification.createNotificationChannel(getApplicationContext(), channel_id, Integer.parseInt(sound), Integer.parseInt(mode));
        //Notification.notificationTime(main.this, (Notification.calculateNextTime(getApplicationContext())) * 60 * 1000);


        /*
        Intent intent = new Intent(main.this,notification_broadcast.class);
        long ten_sec = 1;
        intent.putExtra("time", ten_sec);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(main.this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long currtime = System.currentTimeMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, currtime + ten_sec, pendingIntent);
*/



        ////////////////////////////////////////////////////////////////////////////////////////////
        //PROFILE IMAGE

        if (gender.equals("male")){
            gender_profile.setImageResource(R.drawable.boy);
            goal_water = 3700;
        }
        else if (gender.equals("female")){
            gender_profile.setImageResource(R.drawable.girl);
            goal_water = 2700;
        }


        ////////////////////////////////////////////////////////////////////////////////////////////
        //GREETING --- GOOD____

        if (Integer.parseInt(hours) < 12){
            goodMorning.setText("Good Morning");
            goodMorning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 55);
        }
        else if (12 <= Integer.parseInt(hours) && Integer.parseInt(hours) < 18){
            goodMorning.setText("Good Afternoon");
            goodMorning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        }
        else if (18 <= Integer.parseInt(hours) && Integer.parseInt(hours) <= 24){
            goodMorning.setText("Good Evening");
            goodMorning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 55);
        }



        ////////////////////////////////////////////////////////////////////////////////////////////
        //NEXT REMAINDER IN TEXT

        nextRemainder.setText("Next remainder in: " + Notification.calculateNextTime(getApplicationContext()) + "min");

        if (Integer.parseInt(hours) < Integer.parseInt(wh) || Integer.parseInt(hours) > Integer.parseInt(sh)){
            if (Integer.parseInt(wh) < 12) {
                nextRemainder.setText("Next remainder after " + wh + "am");
            }
            if (Integer.parseInt(wh) >= 12) {
                nextRemainder.setText("Next remainder after " + wh + "pm");
            }
        }

        if (hydration_percentage >= 100 && further.equals("0")){
            nextRemainder.setText("Congratulations your goal is completed");
        }


        ////////////////////////////////////////////////////////////////////////////////////////////
        //HYDRATED TEXT

        hydrate_text_update();


        ////////////////////////////////////////////////////////////////////////////////////////////
        //YOUR GOAL LONG_CLICK_LISTENER

        percentage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water intake goal in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        goal_water = 3700;
                        current_hydrate_status = 0;
                        your_goal_ml.setText((int) goal_water + "ml");

                        percentage.setText(String.valueOf(Math.round((current_hydrate_status / goal_water) * 100)));
                        progressBar.setProgress(Math.round((current_hydrate_status / goal_water) * 100));

                        if((Math.round((current_hydrate_status / goal_water) * 100)) < 100) {
                            waveLoadingView.setProgressValue(Math.round((current_hydrate_status / goal_water) * 100));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goal_water = Integer.parseInt(String.valueOf(input.getText()));
                        your_goal_ml.setText((int) goal_water + "ml");
                        hydration_percentage = (current_hydrate_status / goal_water) * 100;

                        percentage.setText(String.valueOf(Math.round(hydration_percentage)));
                        progressBar.setProgress(Math.round(hydration_percentage));

                        if(Math.round(hydration_percentage) < 100) {
                            waveLoadingView.setProgressValue(Math.round(hydration_percentage));
                        }
                        else {
                            waveLoadingView.setProgressValue(110);
                        }

                        update_hours();
                        insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), 0, (int) goal_water, (int) hydration_percentage);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });


        percent_sign.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water intake goal in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        goal_water = 3700;
                        current_hydrate_status = 0;
                        your_goal_ml.setText((int) goal_water + "ml");

                        percentage.setText(String.valueOf(Math.round((current_hydrate_status / goal_water) * 100)));
                        progressBar.setProgress(Math.round((current_hydrate_status / goal_water) * 100));

                        if((Math.round((current_hydrate_status / goal_water) * 100)) < 100) {
                            waveLoadingView.setProgressValue(Math.round((current_hydrate_status / goal_water) * 100));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goal_water = Integer.parseInt(String.valueOf(input.getText()));
                        your_goal_ml.setText((int) goal_water + "ml");
                        hydration_percentage = (current_hydrate_status / goal_water) * 100;

                        percentage.setText(String.valueOf(Math.round(hydration_percentage)));
                        progressBar.setProgress(Math.round(hydration_percentage));

                        if(Math.round(hydration_percentage) < 100) {
                            waveLoadingView.setProgressValue(Math.round(hydration_percentage));
                        }
                        else {
                            waveLoadingView.setProgressValue(110);
                        }

                        update_hours();
                        insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), 0, (int) goal_water, (int) hydration_percentage);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });


        your_goal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water intake goal in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        goal_water = 3700;
                        current_hydrate_status = 0;
                        your_goal_ml.setText((int) goal_water + "ml");

                        percentage.setText(String.valueOf(Math.round((current_hydrate_status / goal_water) * 100)));
                        progressBar.setProgress(Math.round((current_hydrate_status / goal_water) * 100));

                        if((Math.round((current_hydrate_status / goal_water) * 100)) < 100) {
                            waveLoadingView.setProgressValue(Math.round((current_hydrate_status / goal_water) * 100));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goal_water = Integer.parseInt(String.valueOf(input.getText()));
                        your_goal_ml.setText((int) goal_water + "ml");
                        hydration_percentage = (current_hydrate_status / goal_water) * 100;

                        percentage.setText(String.valueOf(Math.round(hydration_percentage)));
                        progressBar.setProgress(Math.round(hydration_percentage));

                        if(Math.round(hydration_percentage) < 100) {
                            waveLoadingView.setProgressValue(Math.round(hydration_percentage));
                        }
                        else {
                            waveLoadingView.setProgressValue(110);
                        }

                        update_hours();
                        insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), 0, (int) goal_water, (int) hydration_percentage);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });


        your_goal_ml.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water intake goal in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        goal_water = 3700;
                        current_hydrate_status = 0;
                        your_goal_ml.setText((int) goal_water + "ml");

                        percentage.setText(String.valueOf(Math.round((current_hydrate_status / goal_water) * 100)));
                        progressBar.setProgress(Math.round((current_hydrate_status / goal_water) * 100));

                        if((Math.round((current_hydrate_status / goal_water) * 100)) < 100) {
                            waveLoadingView.setProgressValue(Math.round((current_hydrate_status / goal_water) * 100));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goal_water = Integer.parseInt(String.valueOf(input.getText()));
                        your_goal_ml.setText((int) goal_water + "ml");
                        hydration_percentage = (current_hydrate_status / goal_water) * 100;

                        percentage.setText(String.valueOf(Math.round(hydration_percentage)));
                        progressBar.setProgress(Math.round(hydration_percentage));

                        if(Math.round(hydration_percentage) < 100) {
                            waveLoadingView.setProgressValue(Math.round(hydration_percentage));
                        }
                        else {
                            waveLoadingView.setProgressValue(110);
                        }

                        update_hours();
                        insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), 0, (int) goal_water, (int) hydration_percentage);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });


        waveLoadingView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water intake goal in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        goal_water = 3700;
                        current_hydrate_status = 0;
                        your_goal_ml.setText((int) goal_water + "ml");

                        percentage.setText(String.valueOf(Math.round((current_hydrate_status / goal_water) * 100)));
                        progressBar.setProgress(Math.round((current_hydrate_status / goal_water) * 100));

                        if((Math.round((current_hydrate_status / goal_water) * 100)) < 100) {
                            waveLoadingView.setProgressValue(Math.round((current_hydrate_status / goal_water) * 100));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goal_water = Integer.parseInt(String.valueOf(input.getText()));
                        your_goal_ml.setText((int) goal_water + "ml");
                        hydration_percentage = (current_hydrate_status / goal_water) * 100;

                        percentage.setText(String.valueOf(Math.round(hydration_percentage)));
                        progressBar.setProgress(Math.round(hydration_percentage));

                        if(Math.round(hydration_percentage) < 100) {
                            waveLoadingView.setProgressValue(Math.round(hydration_percentage));
                        }
                        else {
                            waveLoadingView.setProgressValue(110);
                        }

                        update_hours();
                        insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), 0, (int) goal_water, (int) hydration_percentage);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });












        ////////////////////////////////////////////////////////////////////////////////////////////
        //CUSTOM ICON LONG_CLICK_LISTENER

        wc1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user_custom_ml = Integer.parseInt(String.valueOf(input.getText()));
                        if (user_custom_ml < 100){
                            Toast.makeText(main.this, "Please enter a value greater that 100ml", Toast.LENGTH_SHORT).show();
                        }
                        else if (user_custom_ml == 100 || user_custom_ml == 150 || user_custom_ml == 200 || user_custom_ml == 300 || user_custom_ml == 400){
                            Toast.makeText(main.this, "This value already exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            tc1.setText(Integer.toString(user_custom_ml));
                            tc1ml.setText("ml");
                            custom_ml_1.setImageResource(R.drawable.drop);
                            custom.setText(tc1.getText() + "ml");
                            sharedPreferences.edit().putString("custom", Integer.toString(user_custom_ml)).apply();
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

                return true;
            }
        });


        tc1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user_custom_ml = Integer.parseInt(String.valueOf(input.getText()));
                        if (user_custom_ml < 100){
                            Toast.makeText(main.this, "Please enter a value greater that 100ml", Toast.LENGTH_SHORT).show();
                        }
                        else if (user_custom_ml == 100 || user_custom_ml == 150 || user_custom_ml == 200 || user_custom_ml == 300 || user_custom_ml == 400){
                            Toast.makeText(main.this, "This value already exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            tc1.setText(Integer.toString(user_custom_ml));
                            tc1ml.setText("ml");
                            custom_ml_1.setImageResource(R.drawable.drop);
                            custom.setText(tc1.getText() + "ml");
                            sharedPreferences.edit().putString("custom", Integer.toString(user_custom_ml)).apply();
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

                return true;
            }
        });


        tc1ml.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user_custom_ml = Integer.parseInt(String.valueOf(input.getText()));
                        if (user_custom_ml < 100){
                            Toast.makeText(main.this, "Please enter a value greater that 100ml", Toast.LENGTH_SHORT).show();
                        }
                        else if (user_custom_ml == 100 || user_custom_ml == 150 || user_custom_ml == 200 || user_custom_ml == 300 || user_custom_ml == 400){
                            Toast.makeText(main.this, "This value already exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            tc1.setText(Integer.toString(user_custom_ml));
                            tc1ml.setText("ml");
                            custom_ml_1.setImageResource(R.drawable.drop);
                            custom.setText(tc1.getText() + "ml");
                            sharedPreferences.edit().putString("custom", Integer.toString(user_custom_ml)).apply();
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

                return true;
            }
        });


        custom_ml_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user_custom_ml = Integer.parseInt(String.valueOf(input.getText()));
                        if (user_custom_ml < 100){
                            Toast.makeText(main.this, "Please enter a value greater that 100ml", Toast.LENGTH_SHORT).show();
                        }
                        else if (user_custom_ml == 100 || user_custom_ml == 150 || user_custom_ml == 200 || user_custom_ml == 300 || user_custom_ml == 400){
                            Toast.makeText(main.this, "This value already exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            tc1.setText(Integer.toString(user_custom_ml));
                            tc1ml.setText("ml");
                            custom_ml_1.setImageResource(R.drawable.drop);
                            custom.setText(tc1.getText() + "ml");
                            sharedPreferences.edit().putString("custom", Integer.toString(user_custom_ml)).apply();
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

                return true;
            }
        });


        custom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("Enter water in ml");
                final EditText input = new EditText(main.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user_custom_ml = Integer.parseInt(String.valueOf(input.getText()));
                        if (user_custom_ml < 100){
                            Toast.makeText(main.this, "Please enter a value greater that 100ml", Toast.LENGTH_SHORT).show();
                        }
                        else if (user_custom_ml == 100 || user_custom_ml == 150 || user_custom_ml == 200 || user_custom_ml == 300 || user_custom_ml == 400){
                            Toast.makeText(main.this, "This value already exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            tc1.setText(Integer.toString(user_custom_ml));
                            tc1ml.setText("ml");
                            custom_ml_1.setImageResource(R.drawable.drop);
                            custom.setText(tc1.getText() + "ml");
                            sharedPreferences.edit().putString("custom", Integer.toString(user_custom_ml)).apply();
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

                return true;
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////

        your_goal_ml.setText((int) goal_water + "ml");

        percentage.setText(String.valueOf(Math.round((current_hydrate_status / goal_water) * 100)));
        progressBar.setProgress(Math.round((current_hydrate_status / goal_water) * 100));

        if((Math.round((current_hydrate_status / goal_water) * 100)) < 100) {
            waveLoadingView.setProgressValue(Math.round((current_hydrate_status / goal_water) * 100));
        }


        ////////////////////////////////////////////////////////////////////////////////////////////
        //STORING DATA





/*
        insert_data(3,2,1,12,200,3700,12);
        insert_data(4,6,5,12,500,3700,12);
        insert_data(5,3,9,12,300,3700,15);
        insert_data(6,4,10,12,600,3700,15);
        insert_data(7,5,11,12,700,3700,18);
        insert_data(8,6,12,12,800,3700,20);
        insert_data(8,6,12,12,400,3700,20);
        insert_data(8,7,13,12,200,3700,5);
        insert_data(13,7,13,12,300,3700,13);
        insert_data(18,7,13,12,400,3700,24);
*/
        //drop();

        if (checkForTableExists()) {
            Log.i("TABLE------>", "EXISTS");


            myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            Cursor c = myDatabase.rawQuery("SELECT * FROM userdata", null);
            int hoursIndex = c.getColumnIndex("hours");
            int dayIndex = c.getColumnIndex("day");
            int dateIndex = c.getColumnIndex("date");
            int monthIndex = c.getColumnIndex("month");
            int waterIndex = c.getColumnIndex("water");
            int goalIndex = c.getColumnIndex("goal");
            int percentIndex = c.getColumnIndex("percent");
            int idIndex = c.getColumnIndex("id");
            try {
                if (c != null && c.moveToFirst()) {
                    while (c != null) {
                        Log.i("hour------>", c.getString(hoursIndex));
                        Log.i("day------->", Integer.toString(c.getInt(dayIndex)));
                        Log.i("date------->", Integer.toString(c.getInt(dateIndex)));
                        Log.i("month-------->", Integer.toString(c.getInt(monthIndex)));
                        Log.i("water------>", c.getString(waterIndex));
                        Log.i("goal------->", Integer.toString(c.getInt(goalIndex)));
                        Log.i("percent-------->", Integer.toString(c.getInt(percentIndex)));
                        Log.i("id-------->", Integer.toString(c.getInt(idIndex)));

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


            Log.i("TEMP_PERCENT---->", String.valueOf(temp_percent));
            Log.i("TEMP_GOAL----->", String.valueOf(temp_goal));
            Log.i("TEMP_WATER----->", String.valueOf(temp_water));

            hydration_percentage = temp_percent;

            if (temp_date != Integer.parseInt(date)){
                hydration_percentage = 0;
            }

            goal_water = temp_goal;
            current_hydrate_status = (hydration_percentage / 100) * temp_goal;


            Log.i("CURRENT_HYDRATE_STATUS", String.valueOf(current_hydrate_status));


            if (temp_water == 100 || temp_water == 150 || temp_water == 200 || temp_water == 300 || temp_water == 400){
                last_updated = temp_water;
                recent.setText((int) last_updated + "ml");
            }


            your_goal_ml.setText((int) goal_water + "ml");
            percentage.setText(String.valueOf(Math.round(hydration_percentage)));
            progressBar.setProgress(Math.round(hydration_percentage));

            if(Math.round(hydration_percentage) < 100) {
                waveLoadingView.setProgressValue(Math.round(hydration_percentage));
            }
            else {
                waveLoadingView.setProgressValue(110);
            }

            if (Integer.parseInt(date) != temp_date){
                current_hydrate_status = 0;
                hydration_percentage = 0;
            }
        }
        else {
            Log.i("TABLE------>", "DOES NOT EXISTS");
        }





        Log.i("CUSTOM------->", sharedPreferences.getString("custom", "Custom"));
        if (sharedPreferences.getString("custom", "Custom").equals("Custom")){
            tc1.setText("Custom");
            tc1ml.setText("");
            custom.setText("Custom");
            custom_ml_1.setImageResource(R.drawable.add);
        }
        else if (sharedPreferences.getString("custom", "Custom").equals("undefined")){
            tc1.setText("Custom");
            tc1ml.setText("");
            custom.setText("Custom");
            custom_ml_1.setImageResource(R.drawable.add);
        }
        else {
            tc1.setText(sharedPreferences.getString("custom", "Custom"));
            tc1ml.setText("ml");
            custom.setText(sharedPreferences.getString("custom", "Custom") + "ml");
            custom_ml_1.setImageResource(R.drawable.drop);
        }


        //tc1.setText(sharedPreferences.getString("custom", "Custom"));





    ////////////////////////////////////////////////////////////////////////////////////////////
    //END OF ONCREATE
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Notification.calculateNextTime(getApplicationContext()) != 0) {
            callNotification();
        }
        for (int x=0; x<10; x++) {
            Log.i("PAUSE----------", "PAUSE");
        }
        sharedPreferences.edit().putString("main_is_active", "no").apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Notification.calculateNextTime(getApplicationContext()) != 0) {
            callNotification();
        }
        for (int x=0; x<10; x++) {
            Log.i("DESTROYED----------", "DESTROYED");
        }
        sharedPreferences.edit().putString("main_is_active", "no").apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Notification.calculateNextTime(getApplicationContext()) != 0) {
            callNotification();
        }
        nextRemainder.setText("Next remainder in: " + Notification.calculateNextTime(getApplicationContext()) + "min");

        if (Integer.parseInt(hours) < Integer.parseInt(wh) || Integer.parseInt(hours) > Integer.parseInt(sh)){
            if (Integer.parseInt(wh) < 12) {
                nextRemainder.setText("Next remainder after " + wh + "am");
            }
            if (Integer.parseInt(wh) >= 12) {
                nextRemainder.setText("Next remainder after " + wh + "pm");
            }
        }

        if (hydration_percentage >= 100 && further.equals("0")){
            nextRemainder.setText("Congratulations your goal is completed");
        }

        hydrate_text_update();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    //INSERT DATA FUNCTION


    public void insert_data(int hours, int day, int date, int month, int water, int goal, int percent){
        Log.i("INSIDE INSERT----->", "HI IAM INSIDE IT");

        callNotification();

        sharedPreferences.edit().putString("water_log_min", minutes).apply();
        Log.i("LOGEED MINUTES---->", sharedPreferences.getString("water_log_min", ""));

        try {
            myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS userdata (hours INT(3), day INT(2), date INT(3), month INT(3), water INT(5), goal INT(5), percent INT(3), id INTEGER PRIMARY KEY)");

            myDatabase.execSQL("INSERT INTO userdata (hours, day, date, month, water, goal, percent) VALUES (" + hours + "," + day + "," + date + "," + month + "," + water + "," + goal + "," + percent + ")");
            myDatabase.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        nextRemainder.setText("Next remainder in: " + Notification.calculateNextTime(getApplicationContext()) + "min");
        if (hours < Integer.parseInt(wh) || hours > Integer.parseInt(sh)){
            if (Integer.parseInt(wh) < 12) {
                nextRemainder.setText("Next remainder after " + wh + "am");
            }
            if (Integer.parseInt(wh) >= 12) {
                nextRemainder.setText("Next remainder after " + wh + "pm");
            }
        }
        if (percent >= 100 && further.equals("0")){
            nextRemainder.setText("Congratulations your goal is completed");
        }
        hydrate_text_update();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    //CLEAR ALL DATA FUNCTION

    public void drop(){
        try {
            myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            myDatabase.execSQL("DROP TABLE IF EXISTS userdata");
            myDatabase.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
        else {
            myDatabase.close();
            c.close();
            return false;
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
    //UPDATING HOURS AND MINUTES

    public void update_hours(){
        currentTime = Calendar.getInstance().getTime();
        hours = new SimpleDateFormat("HH").format(currentTime);
        minutes = new SimpleDateFormat("mm").format(currentTime);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
    //HYDRATE TEXT UPDATE

    public void hydrate_text_update(){
        if (checkForTableExists()) {
            if (Integer.parseInt(Notification.lastDrinkTime(getApplicationContext())[0]) > 0) {
                if (Integer.parseInt(Notification.lastDrinkTime(getApplicationContext())[0]) >= 24) {
                    if ((Integer.parseInt(Notification.lastDrinkTime(getApplicationContext())[0]) / 24) > 1) {
                        hydrated.setText("Last water intake was " + Integer.parseInt(Notification.lastDrinkTime(getApplicationContext())[0]) / 24 + "days and " + Integer.parseInt(Notification.lastDrinkTime(getApplicationContext())[0]) % 24 + "hrs ago");
                    }
                    else {
                        hydrated.setText("Last water intake was " + Integer.parseInt(Notification.lastDrinkTime(getApplicationContext())[0]) / 24 + "day and " + Integer.parseInt(Notification.lastDrinkTime(getApplicationContext())[0]) % 24 + "hrs ago");
                    }
                }
                else {
                    hydrated.setText("Last water intake was " + Notification.lastDrinkTime(getApplicationContext())[0] + "hrs and " + Notification.lastDrinkTime(getApplicationContext())[1] + "min ago");
                }
            } else if (Integer.parseInt(Notification.lastDrinkTime(getApplicationContext())[0]) == 0 && Integer.parseInt(Notification.lastDrinkTime(getApplicationContext())[1]) > 45) {
                hydrated.setText("Last water intake was " + Notification.lastDrinkTime(getApplicationContext())[1] + "min ago");
            } else {
                hydrated.setText("You are well hydrated");
            }
        }
        else {
            hydrated.setText("Let's start with a glass of water");
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
    //CALL NOTIFICATION

    public void callNotification(){
        Notification.createNotificationChannel(getApplicationContext(), channel_id, Integer.parseInt(sound), Integer.parseInt(mode));
        Notification.notificationTime(main.this, (Notification.calculateNextTime(getApplicationContext())) * 60 * 1000);
        //Notification.notificationTime(main.this, 1000 * 10);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //UPDATE STATUS BUTTON

    public void updateStatus(View view){
        if(water_selected == 0 && custom_selected == true){
            Toast.makeText(main.this, "Press and hold + icon to set custom value", Toast.LENGTH_SHORT).show();
        }
        if(water_selected >= 100 && water_selected == 100 || water_selected == 150 || water_selected == 200 || water_selected == 300 || water_selected == 400){
            last_updated = water_selected;
        }
        current_hydrate_status += water_selected;
        hydration_percentage = (current_hydrate_status / goal_water) * 100;

        percentage.setText(String.valueOf(Math.round(hydration_percentage)));
        progressBar.setProgress(Math.round(hydration_percentage));

        if(Math.round(hydration_percentage) < 100) {
            waveLoadingView.setProgressValue(Math.round(hydration_percentage));
        }
        else {
            waveLoadingView.setProgressValue(110);
        }
        recent.setText((int) last_updated + "ml");

        if (water_selected != 0) {
            update_hours();
            insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), (int) water_selected, (int) goal_water, (int) hydration_percentage);
        }

    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //RECENT BUTTON

    public void recent(View view){
        current_hydrate_status += last_updated;
        hydration_percentage = (current_hydrate_status / goal_water) * 100;

        percentage.setText(String.valueOf(Math.round(hydration_percentage)));
        progressBar.setProgress(Math.round(hydration_percentage));

        if((Math.round((current_hydrate_status / goal_water) * 100)) < 100) {
            waveLoadingView.setProgressValue(Math.round((current_hydrate_status / goal_water) * 100));
        }
        if((Math.round((current_hydrate_status / goal_water) * 100)) >= 100) {
            waveLoadingView.setProgressValue(110);
        }
        recent.setText((int) last_updated + "ml");

        update_hours();
        insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), (int) last_updated, (int) goal_water, (int) hydration_percentage);

        sharedPreferences.edit().putInt("last_updated", (int) last_updated);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //CUSTOM BUTTON

    public void custom(View view){
        if (tc1.getText().equals("Custom")){
            water_selected = 0;
            Toast.makeText(main.this, "Press and hold + icon to set custom value", Toast.LENGTH_SHORT).show();
        }
        else {
            custom.setText(tc1.getText() + "ml");
            current_hydrate_status += Integer.parseInt((String) tc1.getText());
            hydration_percentage = (current_hydrate_status / goal_water) * 100;

            percentage.setText(String.valueOf(Math.round(hydration_percentage)));
            progressBar.setProgress(Math.round(hydration_percentage));

            if ((Math.round((current_hydrate_status / goal_water) * 100)) < 100) {
                waveLoadingView.setProgressValue(Math.round((current_hydrate_status / goal_water) * 100));
            }
            if ((Math.round((current_hydrate_status / goal_water) * 100)) >= 100) {
                waveLoadingView.setProgressValue(110);
            }
            update_hours();
            insert_data(Integer.parseInt(hours), day, Integer.parseInt(date), Integer.parseInt(month), Integer.parseInt((String) tc1.getText()), (int) goal_water, (int) hydration_percentage);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //PROFILE BUTTON

    public void gender_profile(View view){

        Intent intent = new Intent(main.this, profile.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //MENU BUTTON

    public void menu(View view){

        Intent intent = new Intent(main.this, menu.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void body(View view){
        custom_selected = false;
        water_selected = 0;
        normal("100");
        normal("150");
        normal("200");
        normal("300");
        normal("400");
        normal("c1");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void w100ml(View view){
        water_selected = Integer.parseInt((String) t100.getText());
        normal("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("400");
        shrink("c1");
    }
    public void cup(View view){
        water_selected = Integer.parseInt((String) t100.getText());
        normal("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("400");
        shrink("c1");
    }
    public void t100(View view){
        water_selected = Integer.parseInt((String) t100.getText());
        normal("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("400");
        shrink("c1");
    }
    public void t100ml(View view){
        water_selected = Integer.parseInt((String) t100.getText());
        normal("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("400");
        shrink("c1");
    }



    public void w150ml(View view){
        water_selected = Integer.parseInt((String) t150.getText());
        normal("150");
        shrink("100");
        shrink("200");
        shrink("300");
        shrink("400");
        shrink("c1");
    }
    public void mug(View view){
        water_selected = Integer.parseInt((String) t150.getText());
        normal("150");
        shrink("100");
        shrink("200");
        shrink("300");
        shrink("400");
        shrink("c1");
    }
    public void t150(View view){
        water_selected = Integer.parseInt((String) t150.getText());
        normal("150");
        shrink("100");
        shrink("200");
        shrink("300");
        shrink("400");
        shrink("c1");
    }
    public void t150ml(View view){
        water_selected = Integer.parseInt((String) t150.getText());
        normal("150");
        shrink("100");
        shrink("200");
        shrink("300");
        shrink("400");
        shrink("c1");
    }



    public void w200ml(View view){
        water_selected = Integer.parseInt((String) t200.getText());
        normal("200");
        shrink("100");
        shrink("150");
        shrink("300");
        shrink("400");
        shrink("c1");
    }
    public void glass(View view){
        water_selected = Integer.parseInt((String) t200.getText());
        normal("200");
        shrink("100");
        shrink("150");
        shrink("300");
        shrink("400");
        shrink("c1");
    }
    public void t200(View view){
        water_selected = Integer.parseInt((String) t200.getText());
        normal("200");
        shrink("100");
        shrink("150");
        shrink("300");
        shrink("400");
        shrink("c1");
    }
    public void t200ml(View view){
        water_selected = Integer.parseInt((String) t200.getText());
        normal("200");
        shrink("100");
        shrink("150");
        shrink("300");
        shrink("400");
        shrink("c1");
    }



    public void w300ml(View view){
        water_selected = Integer.parseInt((String) t300.getText());
        normal("300");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("400");
        shrink("c1");
    }
    public void big_mug(View view){
        water_selected = Integer.parseInt((String) t300.getText());
        normal("300");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("400");
        shrink("c1");
    }
    public void t300(View view){
        water_selected = Integer.parseInt((String) t300.getText());
        normal("300");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("400");
        shrink("c1");
    }
    public void t300ml(View view){
        water_selected = Integer.parseInt((String) t300.getText());
        normal("300");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("400");
        shrink("c1");
    }



    public void w400ml(View view){
        water_selected = Integer.parseInt((String) t400.getText());
        normal("400");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("c1");
    }
    public void bottle(View view){
        water_selected = Integer.parseInt((String) t400.getText());
        normal("400");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("c1");
    }
    public void t400(View view){
        water_selected = Integer.parseInt((String) t400.getText());
        normal("400");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("c1");
    }
    public void t400ml(View view){
        water_selected = Integer.parseInt((String) t400.getText());
        normal("400");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("c1");
    }



    public void wc1(View view){
        if (tc1.getText().equals("Custom")){
            water_selected = 0;
            custom_selected = true;
        }
        else {
            water_selected = Integer.parseInt((String) tc1.getText());
        }
        normal("c1");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("400");
    }
    public void custom_ml(View view){
        if (tc1.getText().equals("Custom")){
            water_selected = 0;
            custom_selected = true;
        }
        else {
            water_selected = Integer.parseInt((String) tc1.getText());
        }
        normal("c1");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("400");
    }
    public void tc1(View view){
        if (tc1.getText().equals("Custom")){
            water_selected = 0;
            custom_selected = true;
        }
        else {
            water_selected = Integer.parseInt((String) tc1.getText());
        }
        normal("c1");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("400");
    }
    public void tc1ml(View view){
        if (tc1.getText().equals("Custom")){
            water_selected = 0;
            custom_selected = true;
        }
        else {
            water_selected = Integer.parseInt((String) tc1.getText());
        }
        normal("c1");
        shrink("100");
        shrink("150");
        shrink("200");
        shrink("300");
        shrink("400");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void shrink(String name){
        if(name.equals("100")) {
            w100ml.setScaleY(.8f);
            w100ml.setScaleX(.8f);
            cup.setScaleY(.8f);
            cup.setScaleX(.8f);
            t100.setTextSize(22f);
            t100.setPadding(0, 0, 0, 40);
            t100ml.setTextSize(11f);
            t100ml.setPadding(0, 0, 0, 45);
        }
        else if(name.equals("150")) {
            w150ml.setScaleY(.8f);
            w150ml.setScaleX(.8f);
            mug.setScaleY(.8f);
            mug.setScaleX(.8f);
            t150.setTextSize(22f);
            t150.setPadding(0, 0, 0, 40);
            t150ml.setTextSize(11f);
            t150ml.setPadding(0, 0, 0, 45);
        }
        else if(name.equals("200")) {
            w200ml.setScaleY(.8f);
            w200ml.setScaleX(.8f);
            glass.setScaleY(.8f);
            glass.setScaleX(.8f);
            t200.setTextSize(22f);
            t200.setPadding(0, 0, 0, 40);
            t200ml.setTextSize(11f);
            t200ml.setPadding(0, 0, 0, 45);
        }
        else if(name.equals("300")) {
            w300ml.setScaleY(.8f);
            w300ml.setScaleX(.8f);
            big_mug.setScaleY(.8f);
            big_mug.setScaleX(.8f);
            t300.setTextSize(22f);
            t300.setPadding(0, 0, 0, 40);
            t300ml.setTextSize(11f);
            t300ml.setPadding(0, 0, 0, 45);
        }
        else if(name.equals("400")) {
            w400ml.setScaleY(.8f);
            w400ml.setScaleX(.8f);
            bottle.setScaleY(.8f);
            bottle.setScaleX(.8f);
            t400.setTextSize(22f);
            t400.setPadding(0, 0, 0, 40);
            t400ml.setTextSize(11f);
            t400ml.setPadding(0, 0, 0, 45);
        }
        else if(name.equals("c1")) {
            wc1.setScaleY(.8f);
            wc1.setScaleX(.8f);
            custom_ml_1.setScaleY(.8f);
            custom_ml_1.setScaleX(.8f);
            tc1.setTextSize(22f);
            tc1.setPadding(0, 0, 0, 40);
            tc1ml.setTextSize(11f);
            tc1ml.setPadding(0, 0, 0, 45);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void normal(String name){
        if(name.equals("100")) {
            w100ml.setScaleY(1f);
            w100ml.setScaleX(1f);
            cup.setScaleY(1f);
            cup.setScaleX(1f);
            t100.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            t100.setPadding(0, 0, 0, 13);
            t100ml.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t100ml.setPadding(0, 0, 0, 28);
        }
        else if(name.equals("150")) {
            w150ml.setScaleY(1f);
            w150ml.setScaleX(1f);
            mug.setScaleY(1f);
            mug.setScaleX(1f);
            t150.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            t150.setPadding(0, 0, 0, 13);
            t150ml.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t150ml.setPadding(0, 0, 0, 28);
        }
        else if(name.equals("200")) {
            w200ml.setScaleY(1f);
            w200ml.setScaleX(1f);
            glass.setScaleY(1f);
            glass.setScaleX(1f);
            t200.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            t200.setPadding(0, 0, 0, 13);
            t200ml.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t200ml.setPadding(0, 0, 0, 28);
        }
        else if(name.equals("300")) {
            w300ml.setScaleY(1f);
            w300ml.setScaleX(1f);
            big_mug.setScaleY(1f);
            big_mug.setScaleX(1f);
            t300.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            t300.setPadding(0, 0, 0, 13);
            t300ml.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t300ml.setPadding(0, 0, 0, 28);
        }
        else if(name.equals("400")) {
            w400ml.setScaleY(1f);
            w400ml.setScaleX(1f);
            bottle.setScaleY(1f);
            bottle.setScaleX(1f);
            t400.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            t400.setPadding(0, 0, 0, 13);
            t400ml.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            t400ml.setPadding(0, 0, 0, 28);
        }
        else if(name.equals("c1")) {
            wc1.setScaleY(1f);
            wc1.setScaleX(1f);
            custom_ml_1.setScaleY(1f);
            custom_ml_1.setScaleX(1f);
            tc1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            tc1.setPadding(0, 0, 0, 13);
            tc1ml.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tc1ml.setPadding(0, 0, 0, 28);
        }
    }









    ////////////////////////////////////////////////////////////////////////////////////////////////
    //END OF ACTIVITY
}












































/*
    public void insert_data(int hours, int day, int month, int water, int goal, int percent){
        try {
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS userdata (hours INT(3), day INT(2), month INT(3), water INT(5), goal INT(5), percent INT(3), id INTEGER PRIMARY KEY)");

            myDatabase.execSQL("INSERT INTO userdata (hours, day, month, water, goal, percent) VALUES (" + hours + "," + day + "," + month + "," + water + "," + goal + "," + percent + ")");

            //myDatabase.execSQL("DELETE FROM userdata WHERE id = 6");

            /*Cursor c = myDatabase.rawQuery("SELECT * FROM userdata", null);
            int hoursIndex = c.getColumnIndex("hours");
            int dayIndex = c.getColumnIndex("day");
            int monthIndex = c.getColumnIndex("month");
            int waterIndex = c.getColumnIndex("water");
            int goalIndex = c.getColumnIndex("goal");
            int percentIndex = c.getColumnIndex("percent");
            int idIndex = c.getColumnIndex("id");
            c.moveToFirst();
            while (c != null){
                Log.i("hour------>", c.getString(hoursIndex));
                Log.i("day------->", Integer.toString(c.getInt(dayIndex)));
                Log.i("month-------->", Integer.toString(c.getInt(monthIndex)));
                Log.i("water------>", c.getString(waterIndex));
                Log.i("goal------->", Integer.toString(c.getInt(goalIndex)));
                Log.i("percent-------->", Integer.toString(c.getInt(percentIndex)));
                Log.i("id-------->", Integer.toString(c.getInt(idIndex)));

                c.moveToNext();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    */




/*

        Intent intent = new Intent(getApplicationContext(),main.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        Notification notification = null;



        notification = new Notification.Builder(getApplicationContext(),"my_channel_01")
                .setContentTitle("Time to get hydrated")
                .setContentText("Please have a glass of water")
                //.setContentText("message")
                .setWhen(10000)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 5, 5)
                .setContentIntent(pendingIntent)
                .setWhen(25)
                //.setSmallIcon(R.drawable.drop)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.mipmap.ic_launcher))
                //.setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                //.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.addAction(android.R.drawable.sym_action_chat,"Chat",pendingIntent)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .addAction(R.drawable.drop, "snooze", snoozePendingIntent)
                .addAction(R.drawable.drop, "custom", snoozePendingIntent)
                .addAction(R.drawable.drop, "recent", snoozePendingIntent)
                .build();



        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel("my_channel_01",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(1,notification);

*/