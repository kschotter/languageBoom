package com.wordpress.thebomby;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class CountdownActivity extends Activity {

    int counter;
    Timer timer;
    TextView countdownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        countdownText = (TextView) findViewById(R.id.countdownText);
        timer = new Timer();
        counter = 3;
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        counter--;
                        countdownText.setText(String.valueOf(counter));
                        if (counter == 0) {
                            startActivity(new Intent(CountdownActivity.this, GameActivity.class));
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(t, 1000L, 1000L);
    }

}
