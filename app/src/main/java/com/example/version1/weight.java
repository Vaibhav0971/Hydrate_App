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
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Objects;

public class weight extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageView imageView;
    NumberPicker numberPicker;
    String gender;
    int currentSelectedWeight;



    public void next(View view){
        sharedPreferences.edit().putString("weight", Integer.toString(numberPicker.getValue())).apply();
        Intent intent = new Intent(weight.this, time.class);
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


        setContentView(R.layout.weight);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPreferences = this.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);
        imageView = findViewById(R.id.genderImage);


        gender = sharedPreferences.getString("gender", "");


        numberPicker = findViewById(R.id.weight);
        numberPicker.setMinValue(10);
        numberPicker.setMaxValue(500);


        NumberPicker numberPicker2 = findViewById(R.id.unit);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(1);
        numberPicker2.setDisplayedValues( new String[] { "Kg", "Lbs"} );



        if(gender.equals("male")){
            numberPicker.setValue(70);
            imageView.setImageResource(R.drawable.boy);
            sharedPreferences.edit().putString("unit", "kg").apply();
        }
        else if(gender.equals("female")){
            numberPicker.setValue(60);
            imageView.setImageResource(R.drawable.girl);
            sharedPreferences.edit().putString("unit", "kg").apply();
        }
        else {
            numberPicker.setValue(65);
        }



        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Toast.makeText(getApplicationContext(), Integer.toString(newVal), Toast.LENGTH_SHORT).show();
                //sharedPreferences.edit().putString("weight", Integer.toString(newVal)).apply();
            }
        });


        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Toast.makeText(getApplicationContext(), Integer.toString(newVal), Toast.LENGTH_SHORT).show();
                if(newVal == 0){
                    numberPicker.setValue((int) Math.round(numberPicker.getValue()/2.2));
                    sharedPreferences.edit().putString("unit", "kg").apply();
                }
                else if(newVal == 1){
                    numberPicker.setValue((int) Math.round(numberPicker.getValue()*2.2));
                    sharedPreferences.edit().putString("unit", "lbs").apply();
                }
            }
        });









    }
}
