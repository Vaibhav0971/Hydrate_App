package com.example.version1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class menu extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SQLiteDatabase myDatabase;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLE DECLARE





    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ONBACKPRESS

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(menu.this, main.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
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
        setContentView(R.layout.menu);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //SCREEN SETTING

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //INITIALIZE

        sharedPreferences = this.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //VARIABLE INITIALIZE






        ////////////////////////////////////////////////////////////////////////////////////////////

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //BACK BUTTON

    public void back_button(View view){

        Intent intent = new Intent(menu.this, main.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //SUB MENU INTENT

    public void sub_menu_intent(String button_name){

        Intent intent = new Intent(menu.this, sub_menu.class);
        intent.putExtra("button_name", button_name);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //TIPS BUTTON

    public void tips(View view){
        sub_menu_intent("tips");
    }

    public void tips_bg(View view){
        sub_menu_intent("tips");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //GENERAL BUTTON

    public void general(View view){
        sub_menu_intent("general");
    }

    public void general_bg(View view){
        sub_menu_intent("general");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //PERSONAL INFO BUTTON

    public void personal_info(View view){
        sub_menu_intent("personal_info");
    }

    public void personal_info_bg(View view){
        sub_menu_intent("personal_info");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //REMAINDER SCHEDULE BUTTON

    public void remainder_schedule(View view){
        sub_menu_intent("remainder_schedule");
    }

    public void remainder_schedule_bg(View view){
        sub_menu_intent("remainder_schedule");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //REMAINDER SOUND BUTTON

    public void remainder_sound(View view){
        sub_menu_intent("remainder_sound");
    }

    public void remainder_sound_bg(View view){
        sub_menu_intent("remainder_sound");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //REMAINDER MODE BUTTON

    public void remainder_mode(View view){
        sub_menu_intent("remainder_mode");
    }

    public void remainder_mode_bg(View view){
        sub_menu_intent("remainder_mode");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //FURTHER REMAINDER BUTTON

    public void further_remainder(View view){
        sub_menu_intent("further_remainder");
    }

    public void further_remainder_bg(View view){
        sub_menu_intent("further_remainder");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //RESET BUTTON

    public void reset_data(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
        builder.setTitle("Reset all existing data");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
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

    public void reset_data_bg(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
        builder.setTitle("Reset all existing data");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
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

    public void reset(){
        sharedPreferences.edit().putString("gender", "undefined").apply();
        sharedPreferences.edit().putString("weight", "undefined").apply();
        sharedPreferences.edit().putString("unit", "undefined").apply();
        sharedPreferences.edit().putString("wh", "undefined").apply();
        sharedPreferences.edit().putString("wm", "undefined").apply();
        sharedPreferences.edit().putString("sh", "undefined").apply();
        sharedPreferences.edit().putString("sm", "undefined").apply();
        sharedPreferences.edit().putString("userDataAcquired", "false").apply();
        sharedPreferences.edit().putString("custom", "undefined").apply();
        sharedPreferences.edit().putString("further", "undefined").apply();
        sharedPreferences.edit().putString("mode", "undefined").apply();
        sharedPreferences.edit().putString("sound", "undefined").apply();
        sharedPreferences.edit().putString("remainder_schedule", "undefined").apply();
        sharedPreferences.edit().putString("remainder_schedule_min", "undefined").apply();
        sharedPreferences.edit().putString("remainder_schedule_hrs", "undefined").apply();
        sharedPreferences.edit().putString("water_log_min", "undefined").apply();


        try {
            myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            myDatabase.execSQL("DROP TABLE IF EXISTS userdata");
            myDatabase.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Reset successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(menu.this, welcome.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


}
