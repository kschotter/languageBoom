package com.wordpress.thebomby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wordpress.thebomby.device.BombAccessor;
import com.wordpress.thebomby.device.BombListener;

import java.util.Random;

public class GameActivity extends Activity implements BombListener {

    GameState state;
    TextView wordText;

    TextView restartText;

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
        String word = state.nextWord();
        wordText.setText(word);
        BombAccessor.getBomb().showWord(word);
    }

    private void startBomb() {
        BombAccessor.getBomb().setBombListener(this);
    }

    Handler mHandler;

    public void useHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 10000L + new Random(System.currentTimeMillis()).nextInt(50000));
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

                mHandler.removeCallbacks(mRunnable);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        //intent.putExtras(getIntent().getExtras());
                        startActivity(intent);
                    }
                });
            } else {
                mHandler.postDelayed(mRunnable, 1000);
            }
        }
    };

    @Override
    public void nextWord() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateWord();
            }
        });
    }

    @Override
    public void skipWord() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateWord();
            }
        });
    }

}
