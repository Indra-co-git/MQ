package com.indra.mq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class play extends AppCompatActivity {

    Chronometer chronometer;
    Button start,pause,reset,sub,b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,backspace;
    private boolean running;
    private long offset;
    String answer_input;

    TextView ta,tb,ts,fs,ed;
    int a,b,c,s;
//    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        chronometer = findViewById(R.id.c1);
        chronometer.setFormat("Time : %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        //  chronometer.setCountDown(true);
        answer_input = "0";


        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        reset = findViewById(R.id.reset);

        ta = findViewById(R.id.a);
        tb = findViewById(R.id.b);
        ts = findViewById(R.id.score);
        fs = findViewById(R.id.finalscore);

        sub = findViewById(R.id.submit);

//        keyboard cord part
        {
        ed = findViewById(R.id.res);


        b0 = findViewById(R.id.button0);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);
        b9 = findViewById(R.id.button9);

        backspace = findViewById(R.id.back);


        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.length() == 1) {
                    answer_input = "0";
                } else
                    answer_input = answer_input.substring(0, answer_input.length() - 1);
                ed.setText(answer_input);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "1";
                else
                    answer_input += "1";
                ed.setText(answer_input);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "2";
                else
                    answer_input += "2";
                ed.setText(answer_input);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "3";
                else
                    answer_input += "3";
                ed.setText(answer_input);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "4";
                else
                    answer_input += "4";
                ed.setText(answer_input);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "5";
                else
                    answer_input += "5";
                ed.setText(answer_input);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "6";
                else
                    answer_input += "6";
                ed.setText(answer_input);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "7";
                else
                    answer_input += "7";
                ed.setText(answer_input);
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "8";
                else
                    answer_input += "8";
                ed.setText(answer_input);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "9";
                else
                    answer_input += "9";
                ed.setText(answer_input);
            }
        });

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer_input.startsWith("0"))
                    answer_input = "0";
                else
                    answer_input += "0";
                ed.setText(answer_input);
            }
        });
    }


        running = false;
        offset = 0;
        s=0;
        sub.setClickable(false);

        start.setOnClickListener(new View.OnClickListener() {
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

        pause.setOnClickListener(new View.OnClickListener() {
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

        reset.setOnClickListener(new View.OnClickListener() {
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
                answer_input="0";
                ed.setText(answer_input);
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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}