package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class gender extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    public void female(View view){

        sharedPreferences.edit().putString("gender", "female").apply();
        /*
        String username = sharedPreferences.getString("gender", "");
        Log.i("Gender----->", username);

         */


        Intent intent = new Intent(gender.this, weight.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public void male(View view){

        sharedPreferences.edit().putString("gender", "male").apply();
        /*
        String username = sharedPreferences.getString("gender", "");
        Log.i("Gender----->", username);

         */


        Intent intent = new Intent(gender.this, weight.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen


        setContentView(R.layout.gender);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        sharedPreferences = this.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);






    }
}
