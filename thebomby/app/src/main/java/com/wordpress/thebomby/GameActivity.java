package com.wordpress.thebomby;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {

    GameState state;
    TextView wordText;

    TextView restartText;

    TimerTask t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String language = getIntent().getStringExtra(MainActivity.KEY_LANGUAGE);
        state = new GameState(language);
        setContentView(R.layout.activity_game);
        wordText = (TextView) findViewById(R.id.wordText);
        restartText = (TextView) findViewById(R.id.restartText);
        updateWord();
        startBomb();
        useHandler();
    }

    private void updateWord() {
        wordText.setText(state.nextWord());
    }

    private void startBomb() {
        final Timer timer = new Timer();

        t = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean explode = Math.random() > 0.7;
                        boolean nextWord = Math.random() > 0.5;
                        if(explode) {
                            //explode();
                        }
                        if(nextWord) {
                            updateWord();
                        }

                    }
                });
            }
        };
        timer.scheduleAtFixedRate(t, 1000L, 1000L);
    }

    Handler mHandler;
    public void useHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            Log.e("Handlers", "Calls");
            long currentTime = System.currentTimeMillis();
            if(currentTime > state.getTime()) {
                wordText.setText("KABOOM!");
                restartText.setVisibility(View.VISIBLE);
                restartText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        restartText.setVisibility(View.INVISIBLE);
                    }
                });
                t.cancel();
                mHandler.removeCallbacks(mRunnable);
            } else {
                mHandler.postDelayed(mRunnable, 1000);
            }
        }
    };
}
