package com.indra.mq;

import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    Button b1,b2,b3;
    private boolean running;
    private long offset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.c1);
        chronometer.setFormat("Time : %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        b1 = findViewById(R.id.start);
        b2 = findViewById(R.id.pause);
        b3 = findViewById(R.id.reset);

        running = false;
        offset = 0;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running)
                {
                    chronometer.setBase(SystemClock.elapsedRealtime()-offset);
                    chronometer.start();
                    running = true;

                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running)
                {
                    chronometer.stop();
                    offset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;


                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running)
                {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    offset=0;
                    chronometer.stop();
                }
            }
        });

    }
}