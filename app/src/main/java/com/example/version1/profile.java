package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class profile extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView day_text;
    TextView week_text;
    TextView month_text;
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    Date currentTime;
    SQLiteDatabase myDatabase;
    TextView please_drink_some_water;
    TextView y_label;
    TextView x_label;
    TextView m_avg_ml;
    TextView w_avg_ml;
    TextView d_fr;
    TextView avg_c_per;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLE DECLARE

    String year;
    String month;
    String date;
    String hours;

    ArrayList arrayList;
    ArrayList arrayList1;

    ArrayList hrs;
    ArrayList water;
    ArrayList temp_water;

    float temp_hrs = 0;
    int count = 0;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ONBACKPRESS

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(profile.this, main.class);
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
        setContentView(R.layout.profile);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //SCREEN SETTING

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //INITIALIZE

        sharedPreferences = this.getSharedPreferences("com.example.version1", Context.MODE_PRIVATE);
        currentTime = Calendar.getInstance().getTime();
        lineChart = findViewById(R.id.barChart);
        day_text = findViewById(R.id.day);
        week_text = findViewById(R.id.week);
        month_text = findViewById(R.id.month);
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        hrs = new ArrayList<>();
        water = new ArrayList<>();
        temp_water = new ArrayList<>();
        m_avg_ml = findViewById(R.id.m_avg_ml);
        w_avg_ml = findViewById(R.id.w_avg_ml);
        d_fr = findViewById(R.id.d_fr);
        avg_c_per = findViewById(R.id.avg_c_per);

        please_drink_some_water = findViewById(R.id.please_drink_some_water);
        x_label = findViewById(R.id.x_label);
        y_label = findViewById(R.id.y_label);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //VARIABLE INITIALIZE

        year = new SimpleDateFormat("yyyy").format(currentTime);
        month = new SimpleDateFormat("MM").format(currentTime);
        date = new SimpleDateFormat("dd").format(currentTime);
        hours = new SimpleDateFormat("HH").format(currentTime);


        ////////////////////////////////////////////////////////////////////////////////////////////
        //BUILDING CHARTS


        Log.i("OUTSIDE------->", "STARTING MONTH *****************************");
        month();
        Log.i("OUTSIDE------->", "ENDING MONTH *****************************");
        //chartEssentials();
        //lineDataSet.clear();
        Log.i("OUTSIDE------->", "STARTING WEEK *****************************");
        week();
        Log.i("OUTSIDE------->", "ENDING WEEK *****************************");
        //chartEssentials();
        lineDataSet.clear();
        Log.i("OUTSIDE------->", "STARTING DAY *****************************");
        day();
        Log.i("OUTSIDE------->", "ENDING DAY *****************************");
        chartEssentials();


        ////////////////////////////////////////////////////////////////////////////////////////////
        //GRAPH

        day_text.setText(Html.fromHtml("<u>Day</u>"));
        day_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("OUTSIDE------->", "CALLING DAY *****************************");
                day_text.setText(Html.fromHtml("<u>Day</u>"));
                week_text.setText("Week");
                month_text.setText("Month");
                lineDataSet.clear();
                day();
                chartEssentials();
                x_label.setText("hrs");
                Log.i("OUTSIDE------->", "COMPLETED DAY *****************************");
            }
        });

        week_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("OUTSIDE------->", "CALLING WEEK *****************************");
                week_text.setText(Html.fromHtml("<u>Week</u>"));
                day_text.setText("Day");
                month_text.setText("Month");
                lineDataSet.clear();
                week();
                chartEssentials();
                x_label.setText("days");
                Log.i("OUTSIDE------->", "COMPLETED WEEK *****************************");
            }
        });

        month_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("OUTSIDE------->", "CALLING MONTH *****************************");
                month_text.setText(Html.fromHtml("<u>Month</u>"));
                week_text.setText("Week");
                day_text.setText("Day");
                lineDataSet.clear();
                month();
                chartEssentials();
                x_label.setText("days");
                Log.i("OUTSIDE------->", "COMPLETED MONTH *****************************");
            }
        });




        ////////////////////////////////////////////////////////////////////////////////////////////
        //END OF ONCREATE


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUID AND SET CHART ESSENTIALS


    public void chartEssentials(){
        Log.i("REBUILDING CHART--->", "TRUE");
        lineDataSet.setColor(Color.WHITE);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(1f);
        lineDataSet.setCircleColor(Color.WHITE);
        lineChart.getXAxis().setAxisLineColor(Color.WHITE);
        lineChart.getAxisLeft().setAxisLineColor(Color.WHITE);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getXAxis().setEnabled(true);
        lineChart.setDrawGridBackground(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setBackgroundColor(Color.TRANSPARENT);
        lineChart.setGridBackgroundColor(Color.TRANSPARENT);
        lineChart.getAxisLeft().setGridColor(Color.rgb(42,142,255));
        lineChart.getXAxis().setGridColor(Color.rgb(42,142,255));
        lineDataSet.setValueTextSize(0f);
        lineChart.getDescription().setText("");
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineChart.animateY( 800);
        lineChart.setVisibleXRangeMaximum(50);
        lineChart.moveViewToX(0);
        lineChart.invalidate();
        Log.i("REBUILDING CHART--->", "COMPLETED");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CHECK IF TABLE EXISTS

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


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TOP BACK BUTTON

    public void back_button(View view){

        Intent intent = new Intent(profile.this, main.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //DAY CHART FUNCTION


    public void day(){
        count = 0;
        temp_hrs = 0;
        water.clear();
        hrs.clear();
        ArrayList frequency = new ArrayList<>();

        if (checkForTableExists()){
            Log.i("TABLE------>", "EXISTS");
            please_drink_some_water.setVisibility(View.GONE);

            myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            //Cursor c = myDatabase.rawQuery("SELECT * FROM userdata WHERE date = " + date, null);
            Cursor c = myDatabase.rawQuery("SELECT * FROM userdata WHERE (date = " + date + " AND month = " + month + ")", null);
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
                    please_drink_some_water.setVisibility(View.GONE);
                    while (c != null) {

                        Log.i("hour------>", c.getString(hoursIndex));
                        Log.i("day------->", Integer.toString(c.getInt(dayIndex)));
                        Log.i("date------->", Integer.toString(c.getInt(dateIndex)));
                        Log.i("month-------->", Integer.toString(c.getInt(monthIndex)));
                        Log.i("water------>", c.getString(waterIndex));
                        Log.i("goal------->", Integer.toString(c.getInt(goalIndex)));
                        Log.i("percent-------->", Integer.toString(c.getInt(percentIndex)));
                        Log.i("id-------->", Integer.toString(c.getInt(idIndex)));

                        if (count > 0 && temp_hrs == Integer.parseInt(c.getString(hoursIndex))) {
                            //water.remove(count - 1);
                            water.set(count - 1, String.valueOf(Integer.parseInt(c.getString(waterIndex)) + Integer.parseInt((String) water.get(count - 1))));
                        } else {
                            temp_hrs = Integer.parseInt(c.getString(hoursIndex));
                            hrs.add(count, c.getString(hoursIndex));
                            water.add(count, c.getString(waterIndex));
                            count++;
                        }

                        Log.i("HRS------->", String.valueOf(hrs));
                        Log.i("WATER------->", String.valueOf(water));

                        ////////////////////////////////////////////////////////////////////////////////////////////////
                        //UPDATING FREQUENCY TEXT
                        frequency.add(c.getString(waterIndex));
                        ////////////////////////////////////////////////////////////////////////////////////////////////

                        if (c.moveToNext()) { }
                        else { break; }
                    }
                    c.close();
                    myDatabase.close();
                }
                else {
                    Log.i("DATE DATA------>", "DOES NOT EXISTS");
                    please_drink_some_water.setVisibility(View.VISIBLE);
                    lineDataSet = new LineDataSet(arrayList1, null);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            Log.i("HI I AM OUT------->", "hi i am out");
            Log.i("HRS SIZE------->", String.valueOf(hrs.size()));



            ////////////////////////////////////////////////////////////////////////////////////////////////
            d_fr.setText(String.valueOf(frequency.size() + " times / day"));
            ////////////////////////////////////////////////////////////////////////////////////////////////


            if (hrs.size() > 0) {
                for (int i = 0; i < hrs.size(); i++) {
                    arrayList.add(new BarEntry(Integer.parseInt(String.valueOf(hrs.get(i))), Integer.parseInt(String.valueOf(water.get(i)))));
                }
                Log.i("HRS------>", String.valueOf(hrs));
                Log.i("ARRAYLIST------>", String.valueOf(arrayList));
                lineDataSet = new LineDataSet(arrayList, null);
            }
            else {
                Log.i("LIST 1--->", String.valueOf(arrayList1));
                please_drink_some_water.setVisibility(View.VISIBLE);
                lineDataSet = new LineDataSet(arrayList1, null);
            }
        }
        else {
            Log.i("TABLE------>", "DOES NOT EXISTS");
            please_drink_some_water.setVisibility(View.VISIBLE);
            lineDataSet = new LineDataSet(arrayList1, null);
        }
        count = 0;
        temp_hrs = 0;
        water.clear();
        hrs.clear();

        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        //lineChart.notifyDataSetChanged();
        //lineChart.invalidate();
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //WEEK CHART FUNCTION

    public void week(){
        ////////////////////////////////////////////////////////////////////////////////////////////////
        //DECLARING VARIABLES

        Log.i("WEEK CALLED------>", "INSIDE WEEK FUNCTION");


        ArrayList temp_water_array_week = new ArrayList<>();
        ArrayList date_arr_week = new ArrayList<>();
        ArrayList temp_date_arr_week = new ArrayList<>();
        ArrayList final_date_arr_week = new ArrayList<>();
        ArrayList final_water_arr_week = new ArrayList<>();

        int temp_hrs_week = 0;
        int counter = 0;
        int temp_date = 0;
        int i;
        int temp_add_water_week = 0;
        int x;
        int temp_data_holder = 0;
        int w_avg_counter;
        int week_avg = 0;


        ////////////////////////////////////////////////////////////////////////////////////////////////
        //STARTING AND FETCHING TOTAL NUMBER OF DATES

        Log.i("STARING IF------>", "STARTING AND FETCHING TOTAL NUMBER OF DATES");

        if (checkForTableExists()){

            Log.i("TABLE CHECK------>", "IT EXISTS");

            please_drink_some_water.setVisibility(View.GONE);

            myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            Cursor crs = myDatabase.rawQuery("SELECT * FROM userdata WHERE month = " + month, null);

            int dateIndex = crs.getColumnIndex("date");

            try {

                Log.i("TRY------>", "INSIDE TRY");

                if (crs != null && crs.moveToFirst()) {
                    please_drink_some_water.setVisibility(View.GONE);
                    while (crs != null) {

                        Log.i("DATE GOT------->", Integer.toString(crs.getInt(dateIndex)));

                        if (counter > 0 && temp_date == crs.getInt(dateIndex)) {
                            date_arr_week.set(counter - 1, crs.getInt(dateIndex));
                        } else {
                            temp_date = crs.getInt(dateIndex);
                            date_arr_week.add(counter, crs.getInt(dateIndex));
                            counter++;
                        }

                        Log.i("DATE_ARR_STAT------->", String.valueOf(date_arr_week));

                        if (crs.moveToNext()) { }
                        else { break; }
                    }
                    crs.close();
                }
                else {
                    Log.i("DATE DATA------>", "DOES NOT EXISTS");
                    please_drink_some_water.setVisibility(View.VISIBLE);
                    lineDataSet = new LineDataSet(arrayList1, null);
                }
                Log.i("EXITING TRY------>", "EXIT");
            }
            catch (Exception e){
                Log.i("READING CATCH------>", "GOT STACK TRACE");
                e.printStackTrace();
            }

            Log.i("CATCH COMPLETE------>", "YES");



            ////////////////////////////////////////////////////////////////////////////////////////////////
            //STARTING DATE SORTING FOR WEEK CATEGORY


            Log.i("STARTING WEEK SORTING->", "TRUE");

            temp_date_arr_week = (ArrayList) date_arr_week.clone();
            Collections.reverse(temp_date_arr_week);
            Log.i("COPY & CLEAR------>", "COPING AND REVERSING LIST AND CLEARING OLD LIST");
            date_arr_week.clear();

            Log.i("FOR LOOP START------>", "YES");

            for (x = 0; x < temp_date_arr_week.size(); x++){
                if ((Integer.parseInt(date) - 7) < (Integer) temp_date_arr_week.get(x) && (Integer) temp_date_arr_week.get(x) <= Integer.parseInt(date)){
                    temp_data_holder = (int) temp_date_arr_week.get(x);
                    date_arr_week.add(temp_data_holder);
                }
            }

            Log.i("FOR LOOP DONE------>", "YES");
            Log.i("ONLY WEEK ARR------>", String.valueOf(date_arr_week));

            Collections.reverse(date_arr_week);

            Log.i("FINAL ONLY WEEK ARR--->", String.valueOf(date_arr_week));
            Log.i("REVERSING ARR------>", "AGAIN REVERSING THE ARRAY");

            Log.i("ENDING WEEK SORTING->", "TRUE");

            counter = 0;

            ////////////////////////////////////////////////////////////////////////////////////////////////
            //GETTING WATER FOR RESPECTIVE DATES

            Log.i("STARTING FOR LOOP---->", "YES");

            for (i = date_arr_week.size() - 1; i >= 0; i--) {

                Log.i("LOOP------>", "FOR LOOP BEGIN");

                Cursor cs = myDatabase.rawQuery("SELECT * FROM userdata WHERE (date = " + date_arr_week.get(i) + " AND month = " + month + ")", null);
                int hoursIndex = cs.getColumnIndex("hours");
                int waterIndex = cs.getColumnIndex("water");


                Log.i("DATE------>", Integer.toString((Integer) date_arr_week.get(i)));

                try {

                    Log.i("TRY------>", "INSIDE TRY");

                    if (cs != null && cs.moveToFirst()) {
                        please_drink_some_water.setVisibility(View.GONE);
                        while (cs != null) {

                            Log.i("HOUR GOT------>", cs.getString(hoursIndex));
                            Log.i("WATER GOT------>", cs.getString(waterIndex));

                            if (counter > 0 && temp_hrs_week == Integer.parseInt(cs.getString(hoursIndex))) {
                                temp_water_array_week.set(counter - 1, String.valueOf(Integer.parseInt(cs.getString(waterIndex)) + Integer.parseInt((String) temp_water_array_week.get(counter - 1))));
                                //Log.i("IF------>", "INSIDE IF");
                            } else {
                                temp_hrs_week = Integer.parseInt(cs.getString(hoursIndex));
                                temp_water_array_week.add(counter, cs.getString(waterIndex));
                                counter++;
                                //Log.i("ELSE------>", "INSIDE ELSE");
                            }

                            Log.i("WATER ARRAY STAT----->", String.valueOf(temp_water_array_week));

                            if (cs.moveToNext()) { }
                            else { break; }
                        }

                    } else {
                        Log.i("DATE DATA------>", "DOES NOT EXISTS");
                        please_drink_some_water.setVisibility(View.VISIBLE);
                    }

                    Log.i("EXITING TRY------>", "EXIT");

                } catch (Exception e) {

                    Log.i("INSIDE CATCH------>", "GETTING STACK TRACE");

                    e.printStackTrace();
                }

                Log.i("WE ARE OUT BUT------->", "STILL INSIDE FOR LOOP");
                Log.i("WATER ARR FINAL STAT-->", String.valueOf(temp_water_array_week));

                final_date_arr_week.add(date_arr_week.get(i));

                for (int j = 0; j < temp_water_array_week.size(); j++) {
                    //temp_water += (int) water.get(j);
                    //final_water_arr_week.add(temp_water_array_week.get(j));
                    temp_add_water_week += Integer.parseInt((String) temp_water_array_week.get(j));
                }

                final_water_arr_week.add(temp_add_water_week);

                temp_water_array_week.clear();
                temp_hrs_week = 0;
                temp_add_water_week = 0;
                counter = 0;

                Log.i("EXITING FOR LOOP------>", "EXIT");

            }

            //cs.close();
            myDatabase.close();

            Log.i("LOOP------>", "FOR LOOP COMPLETED");

            Log.i("FINAL_DATE_ARR------>", String.valueOf(final_date_arr_week));
            Log.i("FINAL_WATER_ARR------>", String.valueOf(final_water_arr_week));



            ////////////////////////////////////////////////////////////////////////////////////////////////
            //UPDATING WEEKLY AVERAGE TEXT


            for(w_avg_counter = 0; w_avg_counter < final_water_arr_week.size(); w_avg_counter++)
            {
                week_avg += (int) final_water_arr_week.get(w_avg_counter);
            }

            w_avg_ml.setText(String.valueOf(week_avg / final_water_arr_week.size()) + "ml / day");





            ////////////////////////////////////////////////////////////////////////////////////////////////
            //PLACING FETCHED VALUES TO PLOT GRAPH


            Log.i("PLOTTING------>", "FINAL STEP PLOTTING GRAPH USING FINAL VALUES");


            //arrayList.add(new BarEntry(50,8));

            for (int k = final_date_arr_week.size() - 1; k >= 0; k--) {
                arrayList.add(new BarEntry(Integer.parseInt(String.valueOf(final_date_arr_week.get(k))), Integer.parseInt(String.valueOf(final_water_arr_week.get(k)))));
            }
            lineDataSet = new LineDataSet(arrayList, null);


            Log.i("ARRAYLIST VALUES------>", String.valueOf(arrayList));
            Log.i("LINEDATASET VALUES---->", String.valueOf(lineDataSet));

        }
        else {
            Log.i("TABLE------>", "DOES NOT EXISTS");
            please_drink_some_water.setVisibility(View.VISIBLE);
            lineDataSet = new LineDataSet(arrayList1, null);
        }


        Log.i("EXITING WEEK------>", "EXIT WEEK FUNCTION");

        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        //lineChart.notifyDataSetChanged();
        //lineChart.invalidate();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //MONTH CHART FUNCTION


    public void month(){
        ////////////////////////////////////////////////////////////////////////////////////////////////
        //DECLARING VARIABLES

        Log.i("MONTH CALLED------>", "INSIDE MONTH FUNCTION");


        ArrayList temp_water_array_week = new ArrayList<>();
        ArrayList date_arr_week = new ArrayList<>();
        ArrayList final_date_arr_week = new ArrayList<>();
        ArrayList final_water_arr_week = new ArrayList<>();
        ArrayList percentage_arr = new ArrayList<>();

        int temp_hrs_week = 0;
        int counter = 0;
        int temp_date = 0;
        int i;
        int temp_add_water_week = 0;
        int month_avg = 0;
        int m_avg_counter;
        int per_counter;
        int per_avg = 0;


        ////////////////////////////////////////////////////////////////////////////////////////////////
        //STARTING AND FETCHING TOTAL NUMBER OF DATES

        Log.i("STARING IF------>", "STARTING AND FETCHING TOTAL NUMBER OF DATES");

        if (checkForTableExists()){

            Log.i("TABLE CHECK------>", "IT EXISTS");

            please_drink_some_water.setVisibility(View.GONE);

            myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
            Cursor crs = myDatabase.rawQuery("SELECT * FROM userdata WHERE month = " + month, null);

            int dateIndex = crs.getColumnIndex("date");
            int percentIndex = crs.getColumnIndex("percent");

            try {

                Log.i("TRY------>", "INSIDE TRY");

                if (crs != null && crs.moveToFirst()) {
                    please_drink_some_water.setVisibility(View.GONE);
                    while (crs != null) {

                        Log.i("DATE GOT------->", Integer.toString(crs.getInt(dateIndex)));

                        if (counter > 0 && temp_date == crs.getInt(dateIndex)) {
                            date_arr_week.set(counter - 1, crs.getInt(dateIndex));
                        } else {
                            temp_date = crs.getInt(dateIndex);
                            date_arr_week.add(counter, crs.getInt(dateIndex));
                            counter++;
                        }

                        Log.i("DATE_ARR_STAT------->", String.valueOf(date_arr_week));

                        ////////////////////////////////////////////////////////////////////////////////////////////////
                        //UPDATING AVERAGE COMPLETION TEXT
                        percentage_arr.add(crs.getInt(percentIndex));
                        Log.i("ARR AVG COMPLETION TEXT", String.valueOf(percentage_arr));
                        ////////////////////////////////////////////////////////////////////////////////////////////////

                        if (crs.moveToNext()) { }
                        else { break; }
                    }
                    crs.close();
                }
                else {
                    Log.i("DATE DATA------>", "DOES NOT EXISTS");
                    please_drink_some_water.setVisibility(View.VISIBLE);
                }

                Log.i("EXITING TRY------>", "EXIT");

            }
            catch (Exception e){

                Log.i("READING CATCH------>", "GOT STACK TRACE");

                e.printStackTrace();
            }

            Log.i("CATCH COMPLETE------>", "YES");

            counter = 0;

            ////////////////////////////////////////////////////////////////////////////////////////////////
            //GETTING WATER FOR RESPECTIVE DATES

            Log.i("STARTING FOR LOOP---->", "YES");

            for (i = date_arr_week.size() - 1; i >= 0; i--) {

                Log.i("LOOP------>", "FOR LOOP BEGIN");

                Cursor cs = myDatabase.rawQuery("SELECT * FROM userdata WHERE (date = " + date_arr_week.get(i) + " AND month = " + month + ")", null);
                int hoursIndex = cs.getColumnIndex("hours");
                int waterIndex = cs.getColumnIndex("water");


                Log.i("DATE------>", Integer.toString((Integer) date_arr_week.get(i)));

                try {

                    Log.i("TRY------>", "INSIDE TRY");

                    if (cs != null && cs.moveToFirst()) {
                        please_drink_some_water.setVisibility(View.GONE);
                        while (cs != null) {

                            Log.i("HOUR GOT------>", cs.getString(hoursIndex));
                            Log.i("WATER GOT------>", cs.getString(waterIndex));

                            if (counter > 0 && temp_hrs_week == Integer.parseInt(cs.getString(hoursIndex))) {
                                temp_water_array_week.set(counter - 1, String.valueOf(Integer.parseInt(cs.getString(waterIndex)) + Integer.parseInt((String) temp_water_array_week.get(counter - 1))));
                                //Log.i("IF------>", "INSIDE IF");
                            } else {
                                temp_hrs_week = Integer.parseInt(cs.getString(hoursIndex));
                                temp_water_array_week.add(counter, cs.getString(waterIndex));
                                counter++;
                                //Log.i("ELSE------>", "INSIDE ELSE");
                            }

                            Log.i("WATER ARRAY STAT----->", String.valueOf(temp_water_array_week));

                            if (cs.moveToNext()) { }
                            else { break; }
                        }

                    } else {
                        Log.i("DATE DATA------>", "DOES NOT EXISTS");
                        please_drink_some_water.setVisibility(View.VISIBLE);
                    }

                    Log.i("EXITING TRY------>", "EXIT");

                } catch (Exception e) {

                    Log.i("INSIDE CATCH------>", "GETTING STACK TRACE");

                    e.printStackTrace();
                }

                Log.i("WE ARE OUT BUT------->", "STILL INSIDE FOR LOOP");
                Log.i("WATER ARR FINAL STAT-->", String.valueOf(temp_water_array_week));

                final_date_arr_week.add(date_arr_week.get(i));

                for (int j = 0; j < temp_water_array_week.size(); j++) {
                    //temp_water += (int) water.get(j);
                    //final_water_arr_week.add(temp_water_array_week.get(j));
                    temp_add_water_week += Integer.parseInt((String) temp_water_array_week.get(j));
                }

                final_water_arr_week.add(temp_add_water_week);

                temp_water_array_week.clear();
                temp_hrs_week = 0;
                temp_add_water_week = 0;
                counter = 0;

                Log.i("EXITING FOR LOOP------>", "EXIT");

            }

            //cs.close();
            myDatabase.close();

            Log.i("LOOP------>", "FOR LOOP COMPLETED");

            Log.i("FINAL_DATE_ARR------>", String.valueOf(final_date_arr_week));
            Log.i("FINAL_WATER_ARR------>", String.valueOf(final_water_arr_week));



            ////////////////////////////////////////////////////////////////////////////////////////////////
            //UPDATING MONTHLY AVERAGE TEXT


            for(m_avg_counter = 0; m_avg_counter < final_water_arr_week.size(); m_avg_counter++)
            {
                month_avg += (int) final_water_arr_week.get(m_avg_counter);
            }

            m_avg_ml.setText(String.valueOf(month_avg / final_water_arr_week.size()) + "ml / day");


            ////////////////////////////////////////////////////////////////////////////////////////////////
            //UPDATING AVERAGE COMPLETION TEXT


            for(per_counter = 0; per_counter < percentage_arr.size(); per_counter++)
            {
                per_avg += (int) percentage_arr.get(per_counter);
            }

            avg_c_per.setText(String.valueOf(per_avg / percentage_arr.size()) + "%");


            ////////////////////////////////////////////////////////////////////////////////////////////////
            //PLACING FETCHED VALUES TO PLOT GRAPH


            Log.i("PLOTTING------>", "FINAL STEP PLOTTING GRAPH USING FINAL VALUES");


            //arrayList.add(new BarEntry(50,8));

            for (int k = final_date_arr_week.size() - 1; k >= 0; k--) {
                arrayList.add(new BarEntry(Integer.parseInt(String.valueOf(final_date_arr_week.get(k))), Integer.parseInt(String.valueOf(final_water_arr_week.get(k)))));
            }

            Log.i("ARRAYLIST VALUES------>", String.valueOf(arrayList));

            lineDataSet = new LineDataSet(arrayList, null);

            Log.i("LINEDATASET VALUES---->", String.valueOf(lineDataSet));

        }
        else {
            Log.i("TABLE------>", "DOES NOT EXISTS");
            please_drink_some_water.setVisibility(View.VISIBLE);
            lineDataSet = new LineDataSet(arrayList1, null);
        }


        Log.i("EXITING WEEK------>", "EXIT WEEK FUNCTION");

        lineData = new LineData(lineDataSet);
        Log.i("LINEDATA VALUES------>", String.valueOf(lineData));
        lineChart.setData(lineData);
        Log.i("LINECHART VALUES------>", String.valueOf(lineChart));


        //lineChart.notifyDataSetChanged();
        //lineChart.invalidate();
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //END OF ACTIVITY
}



























/*
            Log.i("STARTING FOR LOOP---->", "YES");

            for (i = date_arr_week.size() - 1; i >= 0; i--) {

                Log.i("LOOP------>", "FOR LOOP BEGIN");

                Cursor cs = myDatabase.rawQuery("SELECT * FROM userdata WHERE (date = " + date_arr_week.get(i) + " AND month = " + month + ")", null);
                int hoursIndex = cs.getColumnIndex("hours");
                int waterIndex = cs.getColumnIndex("water");


                Log.i("DATE------>", Integer.toString((Integer) date_arr_week.get(i)));

                try {

                    Log.i("TRY------>", "INSIDE TRY");

                    if (cs != null && cs.moveToFirst()) {
                        please_drink_some_water.setVisibility(View.GONE);
                        while (cs != null) {

                            Log.i("HOUR GOT------>", cs.getString(hoursIndex));
                            Log.i("WATER GOT------>", cs.getString(waterIndex));

                            if (counter > 0 && temp_hrs_week == Integer.parseInt(cs.getString(hoursIndex))) {
                                temp_water_array_week.set(counter - 1, String.valueOf(Integer.parseInt(cs.getString(waterIndex)) + Integer.parseInt((String) temp_water_array_week.get(counter - 1))));
                                //Log.i("IF------>", "INSIDE IF");
                            } else {
                                temp_hrs_week = Integer.parseInt(cs.getString(hoursIndex));
                                temp_water_array_week.add(counter, cs.getString(waterIndex));
                                counter++;
                                //Log.i("ELSE------>", "INSIDE ELSE");
                            }

                            Log.i("WATER ARRAY STAT----->", String.valueOf(temp_water_array_week));

                            if (crs.moveToNext()) { }
                            else { break; }
                        }

                    } else {
                        Log.i("DATE DATA------>", "DOES NOT EXISTS");
                        please_drink_some_water.setVisibility(View.VISIBLE);
                        lineDataSet = new LineDataSet(arrayList1, null);
                    }

                    Log.i("EXITING TRY------>", "EXIT");

                }
                catch (Exception e) {
                    Log.i("INSIDE CATCH------>", "GETTING STACK TRACE");
                    e.printStackTrace();
                }

                Log.i("WE ARE OUT BUT------->", "STILL INSIDE FOR LOOP");
                Log.i("WATER ARR FINAL STAT-->", String.valueOf(temp_water_array_week));

                final_date_arr_week.add(date_arr_week.get(i));

                for (int j = 0; j < temp_water_array_week.size(); j++) {
                    //temp_water += (int) water.get(j);
                    //final_water_arr_week.add(temp_water_array_week.get(j));
                    temp_add_water_week += Integer.parseInt((String) temp_water_array_week.get(j));
                }

                final_water_arr_week.add(temp_add_water_week);

                temp_water_array_week.clear();
                temp_hrs_week = 0;
                temp_add_water_week = 0;
                counter = 0;

                Log.i("EXITING FOR LOOP------>", "EXIT");

            }

*/