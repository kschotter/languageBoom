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
    TimerTask counterTask;
    TextView countdownText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        countdownText = (TextView) findViewById(R.id.countdownText);
        timer = new Timer();
    }

    public void resetTimer() {
        if (counterTask != null) {
            counterTask.cancel();
        }
        counter = 3;
        countdownText.setText("3");
        counterTask = new TimerTask() {
            @Override
            public void run() {
                counter -= 1;
                final boolean end = counter == 0;
                final String text;
                if (end) {
                    cancel();
                    text = "Go!";
                } else {
                    text = String.valueOf(counter);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        countdownText.setText(text);
                        if (end) {
                            Intent intent = new Intent(CountdownActivity.this, GameActivity.class);
                            intent.putExtras(getIntent().getExtras());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(counterTask, 1000L, 1000L);
    }

    public void cancelTimer() {
        counterTask.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetTimer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

}
