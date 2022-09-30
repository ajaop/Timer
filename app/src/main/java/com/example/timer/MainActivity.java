package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private Button start;
    private Button stop;
    private Button reset;
    private TextView time;
    long millisecondTime,timeBuff,startTime,updateTime = 0l;
    int milliseconds,seconds,minutes,hours;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);
        reset = (Button) findViewById(R.id.resetButton);
        time = (TextView) findViewById(R.id.timeView);
        handler = new Handler();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeBuff += millisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                millisecondTime = 0l;
                updateTime = 0l;
                timeBuff = 0l;
                startTime = 0l;
                milliseconds = 0;
                seconds = 0;
                minutes = 0;
                hours = 0;
                time.setText("00:00:00:00");

            }
        });
    }

public Runnable runnable = new Runnable() {
    @Override
    public void run() {
        millisecondTime = SystemClock.uptimeMillis() - startTime;
        updateTime = timeBuff + millisecondTime;
        seconds = (int) (updateTime / 1000);
        minutes = seconds/60;
        hours = minutes / 60;
        seconds = seconds % 60;
        milliseconds = (int) (updateTime % 1000);



        time.setText(String.format("%02d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds) + ":"
                + String.format("%03d", milliseconds));
        handler.postDelayed(this, 0);
    }
};


    // Get the previous state of the stopwatch
    // if the activity has been
    // destroyed and recreated.
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("milliseconds", milliseconds);
        outState.putInt("seconds", seconds);
        outState.putInt("minutes", minutes);
        outState.putInt("hours", hours);
        outState.putLong("millisecondTime", millisecondTime);
        outState.putLong("timeBuff", timeBuff);
        outState.putLong("startTime", startTime);
        outState.putLong("updateTime", updateTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        milliseconds = savedInstanceState.getInt("milliseconds");
        seconds = savedInstanceState.getInt("seconds");
        minutes = savedInstanceState.getInt("minutes");
        hours = savedInstanceState.getInt("hours");
        millisecondTime = savedInstanceState.getLong("millisecondTime");
        timeBuff = savedInstanceState.getLong("timeBuff");
        startTime = savedInstanceState.getLong("startTime");
        updateTime = savedInstanceState.getLong("updateTime");

    }
}



