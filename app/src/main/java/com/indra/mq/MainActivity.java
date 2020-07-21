package com.indra.mq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    Button b1,b2,b3,sub,sign_out;
    private boolean running;
    private long offset;

    FirebaseAuth mauth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    TextView ta,tb,ts,fs;
    int a,b,c,s;
    EditText ed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.c1);
        chronometer.setFormat("Time : %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
      //  chronometer.setCountDown(true);



        b1 = findViewById(R.id.start);
        b2 = findViewById(R.id.pause);
        b3 = findViewById(R.id.reset);

        ta = findViewById(R.id.a);
        tb = findViewById(R.id.b);
        ts = findViewById(R.id.score);
        fs = findViewById(R.id.finalscore);

        sign_out=findViewById(R.id.sign_out);

        sub = findViewById(R.id.submit);
        ed = findViewById(R.id.res);


        running = false;
        offset = 0;
        s=0;
        sub.setClickable(false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running)
                {
                    chronometer.setBase(SystemClock.elapsedRealtime()-offset);
                    chronometer.start();
                    running = true;
                    Random rand = new Random();
                    int int_randoma = rand.nextInt(10);
                    int int_randomb = rand.nextInt(10);
                    ta.setText(String.valueOf(int_randoma));
                    tb.setText(String.valueOf(int_randomb));
                    sub.setClickable(true);
                    ed.setText("");

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

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(SystemClock.elapsedRealtime() - chronometer.getBase() >= 10000)
                {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    running = false;
                    chronometer.stop();

                    fs.setText(String.valueOf(s));
                    sub.setClickable(false);
                    s = 0;


                }
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                a = Integer.valueOf(ta.getText().toString());
                b = Integer.valueOf(tb.getText().toString());
                c = Integer.valueOf(ed.getText().toString().trim());
                if(ed.getText().length()!=0 && c == (a+b) )
                {
                    s++;
                    ts.setText(String.valueOf(s));
                    ed.setText("");
                    Random rand = new Random();
                    int int_randoma = rand.nextInt(10);
                    int int_randomb = rand.nextInt(10);
                    ta.setText(String.valueOf(int_randoma));
                    tb.setText(String.valueOf(int_randomb));
                }
                else
                {
                    ed.setText("");
                    ed.setError("Wrong");

                }

            }
        });

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signOut();
                Intent intent=new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });

    }
}