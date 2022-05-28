package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Objects;

import me.itangqi.waveloadingview.WaveLoadingView;

public class welcome extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 400;

    SharedPreferences sharedPreferences;
    //WaveLoadingView waveLoadingView;
    //SeekBar seekBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.welcome);


        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sharedPreferences = this.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);

        //seekBar = (SeekBar)findViewById(R.id.seekBar);
       // waveLoadingView = (WaveLoadingView)findViewById(R.id.waveLoadingView);
       // waveLoadingView.setProgressValue(80);
/*
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveLoadingView.setProgressValue(progress);
                if (progress < 50){
                    waveLoadingView.setBottomTitle(String.format("%d%%",progress));
                    waveLoadingView.setCenterTitle("");
                    waveLoadingView.setTopTitle("");
                }
                else if (progress < 80){
                    waveLoadingView.setBottomTitle("");
                    waveLoadingView.setCenterTitle(String.format("%d%%",progress));
                    waveLoadingView.setTopTitle("");
                }
                else{
                    waveLoadingView.setBottomTitle("");
                    waveLoadingView.setCenterTitle("");
                    waveLoadingView.setTopTitle(String.format("%d%%",progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

 */






        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sharedPreferences.getString("userDataAcquired", "").equals("true")){
                    Intent intent = new Intent(welcome.this, main.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                else {
                    Intent intent = new Intent(welcome.this, gender.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);




    }
}
