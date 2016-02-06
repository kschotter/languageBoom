package com.wordpress.thebomby;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {

    GameState state;
    TextView wordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String language = getIntent().getStringExtra(MainActivity.KEY_LANGUAGE);
        state = new GameState(language);
        setContentView(R.layout.activity_game);
        wordText = (TextView) findViewById(R.id.wordText);
        updateWord();
        startBomb();
    }

    private void updateWord() {
        wordText.setText(state.nextWord());
    }

    private void startBomb() {
        Timer timer = new Timer();

        TimerTask t = new TimerTask() {
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
}
